package com.example.zhuzzzzzzx.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.Stack;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mbutton_1, mbutton_2, mbutton_3, mbutton_4, mbutton_5, mbutton_6, mbutton_7,
            mbutton_8, mbutton_9, mbutton_0, mbutton_del, mbutton_multiply, mbutton_plus,
            mbutton_minus, mbutton_divide, mbutton_equle;
    private EditText print;

    @Override
    protected void onCreate(Bundle savedInstanceState)   {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        print = (EditText) findViewById(R.id.print);
        mbutton_1 = (Button) findViewById(R.id.button2);
        mbutton_1.setOnClickListener(this);
        mbutton_2 = (Button) findViewById(R.id.button3);
        mbutton_2.setOnClickListener(this);
        mbutton_3 = (Button) findViewById(R.id.button4);
        mbutton_3.setOnClickListener(this);
        mbutton_4 = (Button) findViewById(R.id.button5);
        mbutton_4.setOnClickListener(this);
        mbutton_5 = (Button) findViewById(R.id.button6);
        mbutton_5.setOnClickListener(this);
        mbutton_6 = (Button) findViewById(R.id.button7);
        mbutton_6.setOnClickListener(this);
        mbutton_7 = (Button) findViewById(R.id.button8);
        mbutton_7.setOnClickListener(this);
        mbutton_8 = (Button) findViewById(R.id.button9);
        mbutton_8.setOnClickListener(this);
        mbutton_9 = (Button) findViewById(R.id.button10);
        mbutton_9.setOnClickListener(this);
        mbutton_0 = (Button) findViewById(R.id.button12);
        mbutton_0.setOnClickListener(this);
        mbutton_del = (Button) findViewById(R.id.button11);
        mbutton_del.setOnClickListener(this);
        mbutton_equle = (Button) findViewById(R.id.button);
        mbutton_equle.setOnClickListener(this);
        mbutton_plus = (Button) findViewById(R.id.button13);
        mbutton_plus.setOnClickListener(this);
        mbutton_minus = (Button) findViewById(R.id.button14);
        mbutton_minus.setOnClickListener(this);
        mbutton_multiply = (Button) findViewById(R.id.button15);
        mbutton_multiply.setOnClickListener(this);
        mbutton_divide = (Button) findViewById(R.id.button16);
        mbutton_divide.setOnClickListener(this);

    }

    public void onClick(View v) {
        String str = print.getText().toString();
        switch (v.getId()) {
            case R.id.button2:
                push('1');
                print.setText(str + ((Button) v).getText());
                break;
            case R.id.button3:
                push('2');
                print.setText(str + ((Button) v).getText());
                break;
            case R.id.button4:
                push('3');
                print.setText(str + ((Button) v).getText());
                break;
            case R.id.button5:
                push('4');
                print.setText(str + ((Button) v).getText());
                break;
            case R.id.button6:
                push('5');
                print.setText(str + ((Button) v).getText());
                break;
            case R.id.button7:
                push('6');
                print.setText(str + ((Button) v).getText());
                break;
            case R.id.button8:
                push('7');
                print.setText(str + ((Button) v).getText());
                break;
            case R.id.button9:
                push('8');
                print.setText(str + ((Button) v).getText());
                break;
            case R.id.button10:
                push('9');
                print.setText(str + ((Button) v).getText());
                break;
            case R.id.button12:
                push('0');
                print.setText(str + ((Button) v).getText());
                break;
            case R.id.button13:
                push('+');
                print.setText(str + ((Button) v).getText());
                break;
            case R.id.button14:
                push('-');
                print.setText(str + ((Button) v).getText());
                break;
            case R.id.button15:
                push('*');
                print.setText(str + ((Button) v).getText());
                break;
            case R.id.button16:
                push('/');
                print.setText(str + ((Button) v).getText());
                break;
            case R.id.button11:
                if (str != null && !str.equals("")) {
                    push('c');
                    print.setText(str.substring(0,0));
                }
                break;
            case R.id.button:
                push('=');
                break;
        }
    }

    /** 格式化数据 */
    private static final DecimalFormat mFormat = new DecimalFormat(
            "###############.######");
    /** 堆栈 */
    private Stack<String> mMathStack = new Stack<String>();

    /** 操作数 入栈 */
    private void push(char obj) {
        final int size = mMathStack.size();
        // 清除
        if ('c' == obj) {
            mMathStack.clear();
            print.setText("0");
            return;
        }

        // 操作符号
        if ('+' == obj || '-' == obj || '*' == obj || '/' == obj || '=' == obj) {
            switch (size) {
                case 0:
                    break;
                case 2:
                    if ('=' != obj)
                        mMathStack.set(1, obj + "");// 同时输入两个操作符，后面的操作符替换前面的
                    break;
                case 1:
                    if ('=' != obj)
                        mMathStack.push(obj + "");
                    break;
                case 3:
                    String preResult = mFormat.format(calc());
                    mMathStack.push(preResult);
                    if ('=' != obj)
                        mMathStack.push(obj + "");
                    print.setText(preResult);
                    break;
            }
            return;
        }

        String str = "";
        int location = 0;
        switch (size) {
            case 0:
                mMathStack.push("");
            case 1:
                str = mMathStack.peek();
                break;
            case 2:
                mMathStack.push("");
            case 3:
                location = 2;
                str = mMathStack.peek();
                break;
        }

             if ('0' == obj) {
                if (str.length() == 0 || str.equals("0"))
                    return;
            }
            str += obj;

        if ('.' != obj)
            str = mFormat.format(parseDouble(str));
        mMathStack.set(location, str);
        print.setText(str);
    }

    private double calc() {
        double result = 0.0D;
        if (mMathStack.size() == 3) {
            double right = parseDouble(mMathStack.pop());
            String oper = mMathStack.pop();
            double left = parseDouble(mMathStack.pop());
            if ("+".equals(oper)) {
                result = left + right;
            } else if ("-".equals(oper)) {
                result = left - right;
            } else if ("*".equals(oper)) {
                result = left * right;
            } else if ("/".equals(oper)) {
                if (right != 0.0D)
                    result = left / right;
            }
        }
        return result;
    }

    /** 解析文本数据 */
    private double parseDouble(String str) {
        try {
            return Double.parseDouble(str);
        } catch (NumberFormatException e) {
            return 0.0D;
        }
    }

}