package com.putao.wd.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/4/6.
 */
public class CompanionBlackboards extends CreatePage{

        List<CompanionBlackboard> data;

    private List<String> list = new ArrayList<>();
        public List<CompanionBlackboard> getData() {
            // 遍历集合
        for (int position = 0; position < data.size(); position++) {
            CompanionBlackboard blackboard = data.get(position);
            if (!list.contains(blackboard.getTime())) {
                blackboard.setShowDate(true);
                list.add(blackboard.getTime());
            } else {
                blackboard.setShowDate(false);
            }
        }
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