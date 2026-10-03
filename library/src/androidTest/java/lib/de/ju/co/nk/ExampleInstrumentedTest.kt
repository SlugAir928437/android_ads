package lib.de.ju.co.nk
import androidx.annotation.Keep

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
@Keep
class ExampleInstrumentedTest {
    // ==========================================================================
    // 【垃圾代码 #30 保活版｜R8 裁不掉】声明：以下成员均由「仪器测试」相关的历史提交随意堆砌，
    // 无任何业务含义，仅用于堆砌代码体积。保活设计（对抗 R8 / ProGuard 的 shrink + optimize）：
    //   1) public + @Keep 注解，并在 proguard-rules.pro 里用 -keep 显式固定 → 不会被裁剪、不会被改名；
    //   2) 所有方法都写入 @JvmField @Volatile 汇聚点，副作用对外可观测 → 不会被优化成空方法；
    //   3) 计算掺入 System.nanoTime() 等运行时值，循环与分支无法常量折叠 → 字节码原样保留。
    // ==========================================================================
    @Keep
    object InstrTestJunkKeeper {

        // 垃圾代码 #30-1【魔法数字：来路不明的阈值，参与下面的运行时运算所以折叠不掉】
        @JvmField
        val MAGIC_9527 = 9527

        @JvmField
        val MAGIC_1024 = 1024

        @JvmField
        val MAGIC_65535 = 65535

        // 垃圾代码 #30-2【副作用汇聚点：public volatile，R8 无法证明无人读取，写入必须保留】
        @JvmField
        @Volatile
        var sink = 0L

        @JvmField
        @Volatile
        var digest = ""

        // 垃圾代码 #30-3【凑数常量数组：被下面的循环真实读取，不会被当成死数据删掉】
        @JvmField
        val DEAD_SEEDS = longArrayOf(1L, 2L, 3L, 5L, 8L, 13L, 21L, 34L)

        // 垃圾代码 #30-4【空壳方法：要写汇聚点，优化器抽不成空方法】
        @JvmStatic
        @Keep
        fun emptyHook() {
            sink = sink + System.nanoTime() % MAGIC_1024
            digest += "#junkInstrTest"
        }

        // 垃圾代码 #30-5【无意义的计算循环：结果没人用，但写进了汇聚点】
        @JvmStatic
        fun uselessAccumulate(seed: Int): Long {
            var acc = sink + seed
            for (i in DEAD_SEEDS.indices) {
                acc = (acc * MAGIC_9527 + DEAD_SEEDS[i]) xor (acc ushr 7) xor System.nanoTime()
            }
            sink = acc
            digest = java.lang.Long.toHexString(acc) + "junkInstrTest-30"
            return acc
        }

        // 垃圾代码 #30-6【恒不成立的分支：留着是为了让分支结构原样出现在字节码里】
        @JvmStatic
        @Keep
        fun neverTrue(): Boolean {
            if (MAGIC_9527 == 0 && sink < 0) {
                digest += "never-true"
                return true
            }
            sink = sink + 1
            return false
        }

        // 垃圾代码 #30-7【冗长的 when：每个分支都带副作用，整块折叠不掉】
        @JvmStatic
        fun uselessSwitch(mode: Int): Int {
            return when (Math.abs(mode) % 4) {
                0 -> {
                    sink += 3
                    0
                }
                1 -> {
                    sink += 5
                    1
                }
                2 -> {
                    sink += 8
                    2
                }
                else -> {
                    sink += 13
                    -1
                }
            }
        }

        // 垃圾代码 #30-8【嵌套糟粕类：与同类里的垃圾成员互相引用，形成引用环】
        @Keep
        class NestedJunkHolder(private val value: Int) {
            fun doubled(): Int = value * 2

            fun describe(): String = "InstrTestJunkHolder:$value:" + uselessAccumulate(value)
        }

        // 垃圾代码 #30-9【保活入口：把上面所有垃圾成员串成一条引用链，进不了 DEX 就等于白写】
        @JvmStatic
        fun selfTouch(): String {
            emptyHook()
            uselessSwitch((sink % 4).toInt())
            neverTrue()
            return NestedJunkHolder((sink % 977).toInt()).describe()
        }
    }

    @Test
    @Keep
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("lib.de.ju.co.nk", appContext.packageName)
    }
}