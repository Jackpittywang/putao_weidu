package com.putao.wd.pt_me.child;

import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * Created by Administrator on 2016/5/16.
 */
public class MaxLengthWatcher implements TextWatcher {
    private int maxLen;
    private EditText editText;
    private byte[] previousArray;
    private byte[] currentArray;
    private String currentStr;


    public MaxLengthWatcher(int maxLen, EditText editText) {
        this.maxLen = maxLen;
        this.editText = editText;
    }

    public void afterTextChanged(Editable arg0) {
        // TODO Auto-generated method stub

    }

    public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                  int arg3) {
        // TODO Auto-generated method stub

    }

    public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
        // TODO Auto-generated method stub
        if (editText == null) {
            return;
        }
        Editable editable = editText.getText();

        if (editable.toString() == null) {
            return;
        }

        previousArray = editable.toString().getBytes();


        if (previousArray.length >= maxLen) {
            if (previousArray[maxLen - 2] < 0) {
                currentStr = new String(previousArray);
            } else {
                if (previousArray[maxLen - 1] < 0) {
                    currentStr = new String(previousArray, 0, previousArray.length - 1);
                } else {
                    currentStr = new String(previousArray);
                }
            }
        } else {
            currentStr = new String(previousArray);
        }

        editText.setText(currentStr);
        editable = editText.getText();
        Selection.setSelection(editable, editable.length());

//        if(len > maxLen)
//        {
//            int selEndIndex = Selection.getSelectionEnd(editable);
//            String str = editable.toString();
//
//            //截取新字符串
//            String newStr ="";
//            int i = 1;
//            while (newStr.getBytes().length < maxLen){
//                newStr = str.substring(0,i++);
//            }
//
//            int selEndIndexByte = newStr.getBytes().length;
//            editText.setText(newStr);
//            editable = editText.getText();
//
//            //新字符串的长度
//            int newLen = editable.length();
//
//            int newLenByte = editable.toString().getBytes().length;
//            //旧光标位置超过字符串长度
////            if(selEndIndex > newLen)
////            {
////                selEndIndex = editable.length();
////            }
//
//            if(len > newLenByte){
//                selEndIndex = editable.length();
//            }
//
//            //设置新光标所在的位置
//            Selection.setSelection(editable, selEndIndex);
//
//        }
    }
}
