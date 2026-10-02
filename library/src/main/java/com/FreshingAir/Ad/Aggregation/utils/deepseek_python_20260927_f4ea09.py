#!/usr/bin/env python3
# -*- coding: utf-8 -*-

"""
混乱Java代码生成器 v3
- 包名固定为 io.github.ff.myapp
- 生成的代码保证可以编译通过
- 包含多层嵌套内部类
- 混乱但合法
"""

import random
import string
import sys

FIXED_PACKAGE = "io.github.ff.myapp"


class ChaoticJavaCodeGenerator:
    def __init__(self):
        self.class_name = None
        self.indent_unit = "    "
        self.unique_counter = 0

    def uid(self):
        """全局唯一标识符，保证生成的标识符不重复"""
        self.unique_counter += 1
        return self.unique_counter

    def generate_random_string(self, length=8):
        """生成随机字符串（只含合法标识符字符）"""
        return ''.join(random.choices(string.ascii_letters, k=length))

    def generate_chaotic_class_name(self, base_type="Activity"):
        """生成混乱但合法的类名"""
        patterns = [
            lambda: ''.join(random.choice([c.upper(), c.lower()]) for c in base_type),
            lambda: f"{base_type}_{self.uid()}_{random.randint(0, 999)}",
            lambda: f"_{base_type}{self.generate_random_string(3)}",
            lambda: f"{base_type}__{self.generate_random_string(4).upper()}__{self.uid()}",
            lambda: f"{base_type}{self.uid()}{self.generate_random_string(2).upper()}",
            lambda: f"{base_type}{base_type}{self.uid()}",
            lambda: base_type.lower() + self.generate_random_string(3) + str(self.uid()),
            lambda: base_type.upper() + str(self.uid()),
            lambda: f"Xx_{base_type}_xX_{self.uid()}",
            lambda: f"{base_type}{random.choice(['Hao', 'Luan', 'Sha', 'Meng', 'Kun'])}{self.uid()}",
            lambda: f"{random.choice(['My', 'The', 'A', 'An', 'This', 'That'])}{base_type}{self.uid()}",
            lambda: f"{base_type}V{self.uid()}",
            lambda: f"{base_type}${self.generate_random_string(3)}",
            lambda: f"{random.choice(['Zhu', 'Ce', 'Shi', 'Yong', 'Kong'])}{base_type}{self.uid()}",
            lambda: ''.join(random.choice([c.upper(), c.lower()]) for c in f"{base_type}Main{self.uid()}"),
        ]
        return random.choice(patterns)()

    def generate_weird_variable_name(self):
        """生成奇怪的但合法的变量名（不含 Java 关键字）"""
        keywords = {'null', 'true', 'false', 'class', 'new', 'int', 'for', 'if', 'else',
                    'while', 'do', 'return', 'void', 'public', 'private', 'static'}
        while True:
            patterns = [
                lambda: 'var_' + self.generate_random_string(3) + str(self.uid()),
                lambda: 'x' + str(self.uid()),
                lambda: '_' + self.generate_random_string(5) + str(self.uid()),
                lambda: 'temp_' + self.generate_random_string(4).upper() + str(self.uid()),
                lambda: 'VAR' + str(self.uid()),
                lambda: ''.join(random.choices('abcdefghijklmnopqrstuvwxyz', k=1)) + str(self.uid()),
                lambda: 'this_is_a_very_long_variable_name_' + self.generate_random_string(6) + str(self.uid()),
                lambda: ''.join(random.choice([c.upper(), c.lower()]) for c in self.generate_random_string(5)) + str(self.uid()),
                lambda: 'o' * random.randint(1, 3) + '0' * random.randint(1, 3) + str(self.uid()),
                lambda: 'l' * random.randint(1, 4) + '1' * random.randint(1, 3) + str(self.uid()),
            ]
            name = random.choice(patterns)()
            if name not in keywords and (name[0].isalpha() or name[0] == '_'):
                return name

    def generate_random_exception(self):
        """生成随机的异常抛出语句（合法）"""
        exceptions = [
            'throw new RuntimeException("Unknown error: " + System.currentTimeMillis());',
            'throw new IllegalStateException("State corrupted at line " + Thread.currentThread().getStackTrace()[1].getLineNumber());',
            'throw new NullPointerException("Something is null, but we don\'t know what");',
            f'throw new IllegalArgumentException("Invalid argument: {self.generate_random_string(6)}");',
            'throw new Error("Fatal error occurred in " + this.getClass().getName());',
            'throw new UnsupportedOperationException("Not implemented yet, maybe never will be");',
            'throw new ArrayIndexOutOfBoundsException("Index out of bounds: " + (int)(Math.random() * 1000));',
            'throw new ClassCastException("Cannot cast " + System.identityHashCode(this) + " to something");',
            'throw new StackOverflowError("Too deep, man");',
            'throw new OutOfMemoryError("Need more RAM");',
            'if (true) throw new RuntimeException("Always throwing");',
            'if (Math.random() > 0.5) throw new RuntimeException("50% chance of failure");',
            'if (false) throw new RuntimeException("Never throwing, but still scary");',
        ]
        return random.choice(exceptions)

    def generate_nested_class(self, depth, indent_level):
        """递归生成嵌套内部类"""
        if depth <= 0:
            return ""

        indent = self.indent_unit * indent_level
        inner_indent = self.indent_unit * (indent_level + 1)
        inner_inner_indent = self.indent_unit * (indent_level + 2)

        nested_class_name = random.choice([
            'Inner' + self.generate_random_string(3).capitalize() + str(self.uid()),
            'Nested' + self.generate_random_string(4).capitalize(),
            '_Inner_' + self.generate_random_string(3),
            'X' + self.generate_random_string(5).capitalize(),
            'A' * random.randint(1, 2) + 'B' * random.randint(1, 2) + str(self.uid()),
        ])

        modifier = random.choice(['private static', 'private', 'protected static', 'public static', 'static'])

        is_abstract = random.random() > 0.7
        if is_abstract:
            class_decl = f"{modifier} abstract class {nested_class_name}"
        else:
            class_decl = f"{modifier} class {nested_class_name}"

        lines = []
        lines.append(f"{indent}{class_decl} {{")

        # 内部字段
        for _ in range(random.randint(0, 2)):
            var = self.generate_weird_variable_name()
            var_type = random.choice(['int', 'String', 'Object', 'boolean'])
            lines.append(f"{inner_indent}private {var_type} {var};")
            lines.append(f"{inner_indent}// magic value: {random.randint(1000, 9999)}")

        # 内部方法
        if random.random() > 0.3 and not is_abstract:
            method_name = random.choice([
                'doThing' + str(self.uid()),
                'process' + str(self.uid()),
                '_method' + self.generate_random_string(3),
                'run' + self.generate_random_string(4).capitalize(),
            ])
            return_type = random.choice(['void', 'int', 'String', 'Object'])
            lines.append(f"{inner_indent}public {return_type} {method_name}() {{")

            if return_type == 'void':
                lines.append(f"{inner_inner_indent}// TODO: figure out what this does")
                if random.random() > 0.5:
                    lines.append(f"{inner_inner_indent}try {{")
                    lines.append(f"{inner_inner_indent}    if (Math.random() > 0.9999) {{")
                    lines.append(f"{inner_inner_indent}        {self.generate_random_exception()}")
                    lines.append(f"{inner_inner_indent}    }}")
                    lines.append(f"{inner_inner_indent}}} catch (Exception e) {{")
                    lines.append(f"{inner_inner_indent}    throw new RuntimeException(e);")
                    lines.append(f"{inner_inner_indent}}}")
            elif return_type == 'int':
                lines.append(f"{inner_inner_indent}return {random.randint(-9999, 9999)};")
            elif return_type == 'String':
                lines.append(f"{inner_inner_indent}return \"{self.generate_random_string(8)}\";")
            else:
                lines.append(f"{inner_inner_indent}return null;")

            lines.append(f"{inner_indent}}}")

        # 递归嵌套
        if depth > 1:
            lines.append(self.generate_nested_class(depth - 1, indent_level + 1))

        lines.append(f"{indent}}}")

        return '\n'.join(lines)

    def generate_chaotic_method(self, indent_level=1):
        """生成混乱的方法（类中的成员方法）"""
        indent = self.indent_unit * indent_level
        body_indent = self.indent_unit * (indent_level + 1)
        inner_indent = self.indent_unit * (indent_level + 2)
        inner2_indent = self.indent_unit * (indent_level + 3)

        method_name = random.choice([
            'doSomething' + self.generate_random_string(3).capitalize(),
            'process' + str(self.uid()),
            'handle_' + self.generate_random_string(4).lower(),
            'execute' + self.generate_random_string(3).upper(),
            'run' + self.generate_random_string(5),
            'init' + self.generate_random_string(3),
            '_' + self.generate_random_string(5) + str(self.uid()),
            'do' + self.generate_random_string(4).capitalize(),
            ''.join(random.choice([c.upper(), c.lower()]) for c in 'processData') + str(self.uid()),
        ])

        params = []
        for _ in range(random.randint(0, 3)):
            param_type = random.choice(['int', 'String', 'Object', 'boolean', 'double', 'long'])
            param_name = self.generate_weird_variable_name()
            params.append(f'{param_type} {param_name}')

        return_type = random.choice(['void', 'int', 'String', 'Object', 'boolean', 'long', 'double'])

        lines = []
        lines.append(f"{indent}private {return_type} {method_name}({', '.join(params)}) {{")

        # 嵌套 if
        if random.random() > 0.3:
            lines.append(f"{body_indent}if (true) {{")
            lines.append(f"{inner_indent}if (false) {{")
            lines.append(f"{inner2_indent}// dead code, but compiles")
            lines.append(f"{inner2_indent}int {self.generate_weird_variable_name()} = {random.randint(0, 100)};")
            lines.append(f"{inner_indent}}} else if (Math.random() > 0.999) {{")
            lines.append(f"{inner2_indent}{self.generate_random_exception()}")
            lines.append(f"{inner_indent}}} else {{")
            lines.append(f"{inner2_indent}// nothing to see here")
            lines.append(f"{inner_indent}}}")
            lines.append(f"{body_indent}}}")

        # while 循环
        if random.random() > 0.5:
            loop_var = self.generate_weird_variable_name()
            limit = random.randint(1, 100)
            lines.append(f"{body_indent}int {loop_var} = 0;")
            lines.append(f"{body_indent}while (true) {{")
            lines.append(f"{inner_indent}{loop_var}++;")
            lines.append(f"{inner_indent}if ({loop_var} > {limit}) break;")
            lines.append(f"{body_indent}}}")

        # try-catch 嵌套
        if random.random() > 0.4:
            lines.append(f"{body_indent}try {{")
            lines.append(f"{inner_indent}try {{")
            lines.append(f"{inner2_indent}if (Math.random() > 0.9999) {{")
            lines.append(f"{inner2_indent}    {self.generate_random_exception()}")
            lines.append(f"{inner2_indent}}}")
            lines.append(f"{inner_indent}}} catch (RuntimeException e) {{")
            lines.append(f"{inner2_indent}throw new RuntimeException(e);")
            lines.append(f"{inner_indent}}} finally {{")
            lines.append(f"{inner2_indent}// cleanup nothing")
            lines.append(f"{inner_indent}}}")
            lines.append(f"{body_indent}}} catch (Throwable t) {{")
            lines.append(f"{inner_indent}t.printStackTrace();")
            lines.append(f"{body_indent}}}")

        # 随机 return
        if return_type == 'void':
            if random.random() > 0.7:
                lines.append(f"{body_indent}if (Math.random() > 0.5) return;")
        elif return_type == 'int':
            lines.append(f"{body_indent}return {random.randint(-9999, 9999)};")
        elif return_type == 'String':
            lines.append(f"{body_indent}return \"{self.generate_random_string(8)}\";")
        elif return_type == 'Object':
            lines.append(f"{body_indent}return null;")
        elif return_type == 'boolean':
            lines.append(f"{body_indent}return Math.random() > 0.5;")
        elif return_type == 'long':
            lines.append(f"{body_indent}return {random.randint(-99999, 99999)}L;")
        elif return_type == 'double':
            lines.append(f"{body_indent}return Math.random() * {random.randint(1, 1000)};")

        lines.append(f"{indent}}}")
        return '\n'.join(lines)

    def generate_activity(self):
        """生成Android Activity类（含嵌套内部类）"""
        self.class_name = self.generate_chaotic_class_name("Activity")

        methods = []
        for _ in range(random.randint(2, 5)):
            methods.append(self.generate_chaotic_method(indent_level=1))

        nested_classes = []
        for _ in range(random.randint(1, 3)):
            depth = random.randint(2, 4)
            nested_classes.append(self.generate_nested_class(depth, 1))

        tag_name = random.choice([
            'TAG', 'tag', 'Tag', '_TAG_', 'T_A_G', 'LOG_TAG',
            'TAG' + str(self.uid()),
        ])

        key1 = f"key_{self.generate_random_string(5)}"

        code = f"""package {FIXED_PACKAGE};

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import java.util.*;

/**
 * Generated chaotic {self.class_name}
 * WARNING: This code is intentionally confusing but should compile.
 */
public class {self.class_name} extends Activity {{

    private static final String {tag_name} = "{self.generate_random_string(10)}";
    private static int counter = {random.randint(0, 1000)};
    private Object lock = new Object();
    private volatile boolean flag = {'true' if random.random() > 0.5 else 'false'};
    private List<Object> list = new ArrayList<>();
    private Map<String, Object> map = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {{
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {{
            if (savedInstanceState.getBoolean("{key1}", false)) {{
                {self.generate_random_exception()}
            }}
        }}

        try {{
            setContentView(android.R.layout.simple_list_item_1);
        }} catch (Exception e) {{
            throw new RuntimeException("Failed to set content view", e);
        }} finally {{
            Log.d({tag_name}, "onCreate completed at " + System.currentTimeMillis());
        }}

        new Thread(new Runnable() {{
            @Override
            public void run() {{
                while (!Thread.currentThread().isInterrupted()) {{
                    try {{
                        Thread.sleep({random.randint(1, 100)});
                        if (Math.random() > 0.999) {{
                            throw new RuntimeException("Random background crash");
                        }}
                    }} catch (InterruptedException e) {{
                        throw new RuntimeException("Thread interrupted", e);
                    }} catch (RuntimeException e) {{
                        // swallow it, why not
                    }}
                }}
            }}
        }}).start();
    }}

    @Override
    protected void onDestroy() {{
        super.onDestroy();
        if (counter++ > {random.randint(10, 100)}) {{
            {self.generate_random_exception()}
        }}
        System.gc();
    }}

{chr(10).join(methods)}

{chr(10).join(nested_classes)}

    private void handleClick(View v) {{
        if (v == null) {{
            throw new NullPointerException("View is null in handleClick");
        }}
        switch (v.getId()) {{
            case 0:
                {self.generate_random_exception()}
            case 1:
                throw new RuntimeException("Case 1 not implemented");
            default:
                break;
        }}
    }}
}}
"""
        return code

    def generate_application(self):
        """生成Android Application类（含嵌套内部类）"""
        self.class_name = self.generate_chaotic_class_name("Application")

        methods = []
        for _ in range(random.randint(2, 5)):
            methods.append(self.generate_chaotic_method(indent_level=1))

        nested_classes = []
        for _ in range(random.randint(1, 3)):
            depth = random.randint(2, 4)
            nested_classes.append(self.generate_nested_class(depth, 1))

        tag_name = random.choice([
            'APP', 'app', 'App', '_APP_', 'A_P_P',
            'LOG', 'TAG', ''.join(random.choice([c.upper(), c.lower()]) for c in 'apptag'),
        ])

        code = f"""package {FIXED_PACKAGE};

import android.app.Application;
import android.content.Context;
import android.util.Log;
import java.util.*;
import java.util.concurrent.*;

/**
 * Generated chaotic {self.class_name}
 * WARNING: This code is intentionally confusing but should compile.
 */
public class {self.class_name} extends Application {{

    private static {self.class_name} instance;
    private static final Object LOCK = new Object();
    private volatile boolean initialized = false;
    private Map<String, Object> cache = new ConcurrentHashMap<>();
    private ExecutorService executor = Executors.newFixedThreadPool({random.randint(1, 10)});

    @Override
    public void onCreate() {{
        super.onCreate();

        synchronized (LOCK) {{
            if (instance != null) {{
                throw new IllegalStateException("Application already initialized");
            }}
            instance = this;
        }}

        if (!initialized) {{
            initialized = true;
            try {{
                initialize();
            }} catch (Exception e) {{
                throw new RuntimeException("Initialization failed", e);
            }} finally {{
                Log.d("{tag_name}", "Initialization completed: " + initialized);
            }}
        }}

        executor.submit(new Runnable() {{
            @Override
            public void run() {{
                while (true) {{
                    try {{
                        Thread.sleep({random.randint(100, 1000)});
                        if (Math.random() > 0.9999) {{
                            throw new RuntimeException("Background task failed");
                        }}
                    }} catch (InterruptedException e) {{
                        Thread.currentThread().interrupt();
                        break;
                    }} catch (RuntimeException e) {{
                        // ignore
                    }}
                }}
            }}
        }});
    }}

    private void initialize() {{
        Object obj = null;
        if (obj == null) {{
            obj = new Object();
            if (obj != null) {{
                if (obj.equals(obj)) {{
                    cache.put("{self.generate_random_string()}", obj);
                }}
            }}
        }}

        for (int i = 0; i < {random.randint(10, 100)}; i++) {{
            if (i % {random.randint(2, 10)} == 0) {{
                cache.put("key_" + i, new Object() {{
                    @Override
                    public String toString() {{
                        return "{self.generate_random_string()}";
                    }}
                }});
            }}
        }}
    }}

{chr(10).join(methods)}

{chr(10).join(nested_classes)}

    public static {self.class_name} getInstance() {{
        if (instance == null) {{
            throw new IllegalStateException("Application not initialized yet");
        }}
        return instance;
    }}

    public Object getFromCache(String key) {{
        if (key == null) {{
            throw new NullPointerException("Key cannot be null");
        }}
        if (key.isEmpty()) {{
            throw new IllegalArgumentException("Key cannot be empty");
        }}
        if (!cache.containsKey(key)) {{
            throw new NoSuchElementException("Key not found: " + key);
        }}
        return cache.get(key);
    }}
}}
"""
        return code

    def save_to_file(self, code, filename):
        with open(filename, 'w', encoding='utf-8') as f:
            f.write(code)
        print(f"Generated: {filename}")
        for line in code.split('\n'):
            if 'public class' in line:
                print(f"  -> 类名: {line.strip()}")
                break
        print(f"  -> 包名: {FIXED_PACKAGE}")


def main():
    generator = ChaoticJavaCodeGenerator()

    print("混乱Java代码生成器 v3（固定包名 + 可编译 + 嵌套类）")
    print("=" * 60)
    print(f"包名: {FIXED_PACKAGE}")
    print("1. 生成 Activity")
    print("2. 生成 Application")
    print("3. 两者都生成")
    print("4. 预览随机类名")

    choice = input("请选择 (1/2/3/4): ").strip()

    if choice == '1':
        code = generator.generate_activity()
        generator.save_to_file(code, f'{generator.class_name}.java')
    elif choice == '2':
        code = generator.generate_application()
        generator.save_to_file(code, f'{generator.class_name}.java')
    elif choice == '3':
        code = generator.generate_activity()
        generator.save_to_file(code, f'{generator.class_name}.java')
        code = generator.generate_application()
        generator.save_to_file(code, f'{generator.class_name}.java')
    elif choice == '4':
        print("\n随机类名预览:")
        for _ in range(10):
            print(f"  Activity:    {generator.generate_chaotic_class_name('Activity')}")
            print(f"  Application: {generator.generate_chaotic_class_name('Application')}")
            print()
    else:
        print("无效选择")
        sys.exit(1)

    print("\n生成完成！警告：这些代码虽然能编译，但故意设计成混乱的，请勿在生产环境使用！")


if __name__ == '__main__':
    main()
