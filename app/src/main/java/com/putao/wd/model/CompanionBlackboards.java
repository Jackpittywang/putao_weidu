package com.putao.wd.model;

import java.util.List;

/**
 * Created by Administrator on 2016/4/6.
 */
public class CompanionBlackboards extends Page{

        List<CompanionBlackboard> data;

        public List<CompanionBlackboard> getData() {
            return data;
        }

        public void setData(List<CompanionBlackboard> data) {
            this.data = data;
        }

        @Override
        public String toString() {
            return "CompanionBlackboards{" +
                    "data=" + data +
                    '}';
        }
    }