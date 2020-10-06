package com.meng.tools;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class TextLexer {

    private static TextLexer lexer = new TextLexer();

    public static ArrayList<String> analyze(String s) {
        ArrayList<String> arrayList = lexer.scannerAll(s);
        lexer.init();
        int size = arrayList.size();
        for (int i = 0;i < size;++i) {
            String ele = arrayList.get(i);
            if (ele.startsWith("\"") && ele.endsWith("\"")) {
                arrayList.set(i, ele.substring(1).substring(0, ele.length() - 2));
            }
        }
        return arrayList;
    }

    private static HashSet<String> keys = new HashSet<String>(){
        {
            Collections.addAll(this, new String[]{
                                   "draw",
                                   "spell"
                               });
        }
    };

    private ArrayList<String> result;
    private String text;
    private int row_number;

    public static boolean isAlpha(char c) {
        return ((c <= 'z') && (c >= 'a')) || ((c <= 'Z') && (c >= 'A')) || (c == '_');
    }

    public static boolean isNumber(char c) {
        return (c >= '0') && (c <= '9');
    }

    private TextLexer() {
        init();
    }

    private boolean isKey(String t) {
        return keys.contains(t);
    }

    private void init() {
        result = new ArrayList<>();
        text = null;
        row_number = 1;
    }

    // 处理整个字符串
    private ArrayList<String> scannerAll(String str) {
        int i = 0;
        char c;
        text = str + '\0';
        while (i < text.length() - 1) {
            c = text.charAt(i);
            if (c == ' ' || c == '\t') {
                i++;
            } else if (c == '\r' || c == '\n') {
                row_number++;
                i++;
            } else {
                i = scannerPart(i);
            }
        }
        return result;
    }

    private int scannerPart(int arg0) {
        int i = arg0;
        char ch = text.charAt(i);
        String s = "";
        // 第一个输入的字符是字母
        if (isAlpha(ch)) {
            s = "" + ch;
            return handleFirstAlpha(i, s);
        } else if (isNumber(ch)) {
            s = "" + ch;
            return handleFirstNum(i, s);
        } else {
            s = "" + ch;
            switch (ch) {
                case ' ':
                case '\n':
                case '\r':
                case '\t':
                    return ++i;
                case '[':
                case ']':
                case '(':
                case ')':
                case '{':
                case '}':
                    addResult(s, "双界符");
                    return ++i;
                case ':':
                    if (text.charAt(i + 1) == '=') {
                        s = s + "=";
                        addResult(s, "界符");
                        return i + 2;
                    } else {
                        addError(row_number, s, "不能识别");
                        return i + 1;
                    }
                case ',':
                case '.':
                case ';':
                    addResult(s, "单界符");
                    return ++i;
                case '\\':
                    if (text.charAt(i + 1) == 'n' || text.charAt(i + 1) == 't' || text.charAt(i + 1) == 'r') {
                        addResult(s + text.charAt(i + 1), "转义");
                        return i + 2;
                    }
                case '\'':
                    // 判断是否为单字符，否则报错
                    return handleChar(i, s);
                case '\"':
                    // 判定字符串
                    return handleString(i, s);
                case '+':
                    return handlePlus(i, s);
                case '-':
                    return handleMinus(i, s);
                case '*':
                case '/':
                    if (text.charAt(i + 1) == '*') {
                        return handleNote(i, s);
                    } else if (text.charAt(i + 1) == '/') {
                        return handleSingleLineNote(i, s);
                    }
                case '!':
                case '=':
                    ch = text.charAt(++i);
                    if (ch == '=') {
                        // 输出运算符
                        s = s + ch;
                        addResult(s, "运算符");
                        return ++i;
                    } else {
                        // 输出运算符
                        addResult(s, "运算符");
                        return i;
                    }
                case '>':
                    return handleMore(i, s);
                case '<':
                    return handleLess(i, s);
                case '%':
                    ch = text.charAt(++i);
                    if (ch == '=') {
                        // 输出运算符
                        s = s + ch;
                        addResult(s, "运算符");
                        return ++i;
                    } else if (ch == 's' || ch == 'c' || ch == 'd' || ch == 'f' || ch == 'l') {
                        // 输出类型标识符
                        s = s + ch;
                        addResult(s, "输出类型标识符");
                        return ++i;
                    } else {
                        // 输出求余标识符
                        addResult(s, "求余标识符");
                        return i;
                    }
                default:
                    return handleString(i, s);
            }
        }
    }

    private int handleFirstAlpha(int arg, String arg0) {
        int i = arg;
        String s = arg0;
        char ch = text.charAt(++i);
        while (isAlpha(ch) || isNumber(ch)) {
            s = s + ch;
            ch = text.charAt(++i);
        }
        if (s.length() == 1) {
            addResult(s, "字符常数");
            return i;
        }
        // 到了结尾
        if (isKey(s)) {
            // 输出key
            addResult(s, "关键字");
            return i;

        } else if (keys.contains(s)) {
            // 输出普通的标识符
            addResult(s, "普通标识符");
            return i;
        } else {
            addError(row_number, s, "无法识别的标识符");
            return i;
        }

    }

    private int handleFirstNum(int arg, String arg0) {
        int i = arg;
        char ch = text.charAt(++i);
        String s = arg0;
        while (isNumber(ch)) {
            s = s + ch;
            ch = text.charAt(++i);
        }
        if ((text.charAt(i) == ' ') || (text.charAt(i) == '\t') || (text.charAt(i) == '\n') || (text.charAt(i) == '\r') || (text.charAt(i) == '\0') || ch == ';' || ch == ',') {
            // 到了结尾，输出数字
            addResult(s, "整数");
            return i;
        } else if (ch == 'E') {
            if (text.charAt(i + 1) == '+') {
                s = s + ch;
                ch = text.charAt(++i);
                s = s + ch;
                ch = text.charAt(++i);
                while (isNumber(ch)) {
                    s = s + ch;
                    ch = text.charAt(++i);
                }
                if (ch == '\r' || ch == '\n' || ch == ';' || ch == '\t') {
                    addResult(s, "科学计数");
                    return ++i;
                } else {
                    addError(i, s, "浮点数错误");
                    return i;
                }
            } else if (isNumber(text.charAt(i + 1))) {
                s = s + ch;
                ch = text.charAt(++i);
                while (isNumber(ch)) {
                    s = s + ch;
                    ch = text.charAt(++i);
                }
                if (ch == '\r' || ch == '\n' || ch == ';' || ch == '\t') {
                    addResult(s, "科学计数");
                    return ++i;
                } else {
                    addError(row_number, s, "浮点数错误");
                    return i;
                }
            } else {
                addError(row_number, s, "科学计数法错误");
                return ++i;
            }
        } else if (text.charAt(i) == '.' && isNumber(text.charAt(i + 1))) {
            s = s + '.';
            ch = text.charAt(++i);
            while (isNumber(ch)) {
                s = s + ch;
                ch = text.charAt(++i);      
            }
            if (ch == 'E') {
                if (text.charAt(i + 1) == '+') {
                    s = s + ch;
                    ch = text.charAt(++i);
                    s = s + ch;
                    ch = text.charAt(++i);
                    while (isNumber(ch)) {
                        s = s + ch;
                        ch = text.charAt(++i);
                    }
                    if (ch == '\r' || ch == '\n' || ch == ';' || ch == '\t') {
                        addResult(s, "科学计数");
                        return ++i;
                    } else {
                        addError(i, s, "浮点数错误");
                        return i;
                    }
                } else if (isNumber(text.charAt(i + 1))) {
                    s = s + ch;
                    ch = text.charAt(++i);
                    while (isNumber(ch)) {
                        s = s + ch;
                        ch = text.charAt(++i);
                    }
                    if (ch == '\r' || ch == '\n' || ch == ';' || ch == '\t') {
                        addResult(s, "科学计数");
                        return ++i;
                    } else {
                        addError(row_number, s, "浮点数错误");
                        return i;
                    }
                } else {
                    addError(row_number, s, "科学计数法错误");
                    return ++i;
                }
            } else if (ch == '\n' || ch == '\r' || ch == '\t' || ch == ' ' || ch == '\0' || ch != ',' || ch != ';') {
                addResult(s, "浮点数");
                return i;
            } else if (ch == '+' || ch == '-' || ch == '*' || ch == '/' || ch == '\0') {
                addResult(s, "浮点数");
                return i;
            } else {
                while (ch != '\n' && ch != '\t' && ch != ' ' && ch != '\r' && ch != '\0' && ch != ';' && ch != '.' && ch != ',') {
                    s = s + ch;
                    ch = text.charAt(++i);                      
                }
                addError(row_number, s, "不合法的字符");
                return i;
            }
        } else if (ch == '+' || ch == '-' || ch == '*' || ch == '/' || ch == '\0') {
            addResult(s, "整数");
            return i;
        } else {
            do {
                ch = text.charAt(i++);
                s = s + ch;
            } while ((text.charAt(i) != ' ') && (text.charAt(i) != '\t') && (text.charAt(i) != '\n') && (text.charAt(i) != '\r') && (text.charAt(i) != '\0'));
            addError(row_number, s, "错误的标识符");
            return i;
        }
    }
    private int handleChar(int arg, String arg0) {
        String s = arg0;
        int i = arg;
        char ch = text.charAt(++i);
        while (ch != '\'') {
            if (ch == '\r' || ch == '\n') {
                row_number++;
            } else if (ch == '\0') {
                addError(row_number, s, "单字符错误");
                return i;
            }
            s = s + ch;
            ch = text.charAt(++i);
        }
        s = s + ch;
        System.out.println(s);
        if (s.length() == 3 || s.equals("\'" + "\\" + "t" + "\'") || s.equals("\'" + "\\" + "n" + "\'") || s.equals("\'" + "\\" + "r" + "\'")) {
            addResult(s, "单字符");
        } else 
            addError(row_number, s, "字符溢出");
        return ++i;
    }

    // 单行注释处理
    private int handleSingleLineNote(int arg, String arg0) {
        String s = arg0;
        int i = arg;
        char ch = text.charAt(++i);
        while (ch != '\r' && ch != '\n' && ch != '\0') {
            s = s + ch;
            ch = text.charAt(++i);          
        }
        addResult(s, "单行注释");
        return i;
    }

    // 字符串处理
    private int handleString(int arg, String arg0) {
        String s = arg0;
        int i = arg;
        char ch = text.charAt(++i);
        if (arg0.startsWith("\"")) {
            while (ch != '"') {
                if (ch == '\r' || ch == '\n') {
                    row_number++;
                } else if (ch == '\0') {
                    addError(row_number, s, "字符串没有闭合");
                    return i;
                }
                s = s + ch;
                ch = text.charAt(++i);
            }
        } else {
            while (ch != ' ') {
                if (ch == '\r' || ch == '\n') {
                    row_number++;
                } else if (ch == '\0') {
                    if (arg + s.length() != text.length() - 1) {
                        addError(row_number, s, "字符串没有闭合");
                    } else {
                        addResult(s , "字符串"); 
                    }
                    return i;    
                }
                s = s + ch;
                ch = text.charAt(++i);
            }  
        }
        s = s + ch;
        addResult(s, "字符串");
        return ++i;
    }

    private int handlePlus(int arg, String arg0) {
        int i=arg;
        char ch = text.charAt(++i);
        String s = arg0;
        if (ch == '+') {
            // 输出运算符
            s = s + ch;
            addResult(s, "运算符");
            return ++i;
        } else if (ch == '=') {
            s = s + ch;
            // 输出运算符
            addResult(s, "运算符");
            return ++i;
        } else {
            // 输出运算符
            addResult(s, "运算符");
            return i;
        }
    }

    // 处理注释,没有考虑不闭合的情况
    private int handleNote(int arg, String arg0) {
        int i = arg;
        char ch=text.charAt(++i);
        String s = arg0 + ch;
        ch = text.charAt(++i);
        while (ch != '*' || ((i + 1) < text.length()) && text.charAt(i + 1) != '/') {
            s = s + ch;
            if (ch == '\r' || ch == '\n') {
                row_number++;
            } else if (ch == '\0') {
                addError(row_number, s, "注释没有闭合");
                return i;
            }
            ch = text.charAt(++i);
        }
        s = s + "*/";
        addResult(s, "注释");
        return i + 2;
    }

    // 处理减号
    private int handleMinus(int arg, String arg0) {
        int i = arg;
        char ch = text.charAt(++i);
        String s = arg0;
        if (ch == '-') {
            s = s + ch;
            // 输出运算符
            addResult(s, "运算符");
            return ++i;
        } else if (ch == '=') {
            s = s + ch;
            // 输出运算符
            addResult(s, "运算符");
            return ++i;
        } else {
            // 输出运算符
            addResult(s, "运算符");
            return i;
        }
    }

    private int handleMore(int arg, String arg0) {
        int i = arg;
        char ch = text.charAt(++i);
        String s = arg0;
        if (ch == '=') {
            s = s + ch;
            // 输出运算符
            addResult(s, "运算符");
            return ++i;
        } else if (ch == '>') {
            s = s + ch;
            // 输出运算符
            addResult(s, "运算符");
            return ++i;
        } else {
            // 输出运算符
            addResult(s, "运算符");
            return i;
        }
    }

    private int handleLess(int arg, String arg0) {
        int i = arg;
        String s = arg0;
        char ch = text.charAt(++i);
        if (ch == '=') {
            s = s + ch;
            // 输出运算符
            addResult(s, "运算符");
            return ++i;
        } else if (ch == '<') {
            s = s + ch;
            // 输出运算符
            addResult(s, "运算符");
            return ++i;
        } else {
            // 输出运算符
            addResult(s, "运算符");
            return i;
        }
    }

    private void addResult(String rs_value, String rs_name) {
        System.out.println(rs_value + " " + rs_name);
        result.add(rs_value);
    }

    private void addError(int row_num, String rs_value, String rs_name) {
        System.out.println(row_num + " " + rs_value + " " + rs_name);
        result.add(rs_value);
    }

}
