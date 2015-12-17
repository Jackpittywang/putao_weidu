package com.putao.wd.store.product.util;

import android.support.v4.util.ArrayMap;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.putao.wd.model.Norms;
import com.putao.wd.model.ProductNormsSepc;
import com.putao.wd.model.ProductNormsSku;
import com.putao.wd.model.SpecItem;
import com.putao.wd.model.SpecName;
import com.sunnybear.library.util.ListUtils;
import com.sunnybear.library.util.StringUtils;
import com.sunnybear.library.view.select.Tag;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 商品规格工具
 * Created by guchenkai on 2015/12/17.
 */
public class SpecUtils {

    /**
     * 获取商品规格
     *
     * @param spec_item 商品规格json
     * @return 商品规格
     */
    private static List<ArrayMap<String, SpecItem>> getSpecItems(String spec_item) {
        List<ArrayMap<String, SpecItem>> items = new ArrayList<>();
        JSONObject object = JSON.parseObject(spec_item);
        Set<Map.Entry<String, Object>> entries = object.entrySet();
        for (Map.Entry<String, Object> entry : entries) {
            ArrayMap<String, SpecItem> item = new ArrayMap<>();
            String key = entry.getKey();
            SpecItem value = JSON.parseObject(((JSONObject) entry.getValue()).toJSONString(), SpecItem.class);
            item.put(key, value);
            items.add(ListUtils.sortByKey(item));
        }
        return items;
    }

    /**
     * 获取规格种类数
     *
     * @param spec_item
     * @return
     */
    public static int getSpecItemCount(String spec_item) {
        JSONObject object = JSON.parseObject(spec_item);
        return object.size();
    }

    /**
     * 获取规格信息
     *
     * @param spec_name 规格信息json
     * @return 规格信息
     */
    private static List<ArrayMap<String, List<ArrayMap<String, SpecName>>>> getSpecName(String spec_name) {
        List<ArrayMap<String, List<ArrayMap<String, SpecName>>>> names = new ArrayList<>();
        JSONObject object = JSON.parseObject(spec_name);
        Set<String> keys = object.keySet();
        for (String k : keys) {
            ArrayMap<String, List<ArrayMap<String, SpecName>>> name = new ArrayMap<>();
            JSONObject jsonObject = (JSONObject) object.get(k);
            Set<Map.Entry<String, Object>> entries = jsonObject.entrySet();
            List<ArrayMap<String, SpecName>> maps = new ArrayList<>();
            for (Map.Entry<String, Object> entry : entries) {
                ArrayMap<String, SpecName> n = new ArrayMap<>();
                String key = entry.getKey();
                SpecName value = JSON.parseObject(((JSONObject) entry.getValue()).toJSONString(), SpecName.class);
                n.put(key, value);
                maps.add(ListUtils.sortByKey(n));
            }
            name.put(k, maps);
            names.add(ListUtils.sortByKey(name));
        }
        return names;
    }

    /**
     * 组合标签组
     *
     * @return
     */
    public static List<Norms> getNormses(ProductNormsSepc sepc) {
        List<Norms> normses = new ArrayList<>();
        List<ArrayMap<String, SpecItem>> specItems = getSpecItems(sepc.getSpec_item());
        List<ArrayMap<String, List<ArrayMap<String, SpecName>>>> specNames = getSpecName(sepc.getSpec_name());
        for (ArrayMap<String, SpecItem> specItem : specItems) {
            Norms norms = new Norms();
            //商品规格
            for (Map.Entry<String, SpecItem> entry : specItem.entrySet()) {
                String spec_item_id = entry.getKey();
                norms.setSpec_item_id(spec_item_id);
                SpecItem item = entry.getValue();
                norms.setTitle("选择" + item.getName());
                //规格信息
                List<ArrayMap<String, SpecName>> names = getSpecNameByKey(specNames, spec_item_id);
                List<Tag> tags = getTags(names, spec_item_id);
                norms.setTags(tags);
            }
            normses.add(norms);
            Collections.sort(normses, new Comparator<Norms>() {
                @Override
                public int compare(Norms lhs, Norms rhs) {
                    return lhs.getSpec_item_id().compareTo(rhs.getSpec_item_id());
                }
            });
        }
        return normses;
    }


    /**
     * 根据Key查询规格信息
     *
     * @param source 源数据
     * @param key    key
     * @return 查询规格信息
     */
    private static List<ArrayMap<String, SpecName>> getSpecNameByKey(List<ArrayMap<String, List<ArrayMap<String, SpecName>>>> source, String key) {
        for (ArrayMap<String, List<ArrayMap<String, SpecName>>> map : source) {
            Set<String> keys = map.keySet();
            for (String k : keys) {
                if (StringUtils.equals(k, key))
                    return map.get(k);
            }
        }
        return null;
    }

    /**
     * 获取标签组
     *
     * @param names
     * @param spec_item_id
     * @return
     */
    private static List<Tag> getTags(List<ArrayMap<String, SpecName>> names, String spec_item_id) {
        List<Tag> tags = new ArrayList<>();
        for (ArrayMap<String, SpecName> name : names) {
            for (Map.Entry<String, SpecName> entry : name.entrySet()) {
                Tag tag = new Tag();
                tag.setParent_id(spec_item_id);
                tag.setId(entry.getKey());
                SpecName n = entry.getValue();
                tag.setText(n.getText());
                tag.setIcon(n.getIcon());
                tags.add(tag);
            }
        }
        Collections.sort(tags, new Comparator<Tag>() {
            @Override
            public int compare(Tag lhs, Tag rhs) {
                return lhs.getId().compareTo(rhs.getId());
            }
        });
        return tags;
    }

    /**
     * 获得规格对应的商品信息
     *
     * @param source 源数据
     * @param tags   选择tag集合
     * @return 规格对应的商品信息
     */
    public static ProductNormsSku getProductSku(List<ProductNormsSku> source, List<Tag> tags) {
        List<String> ids = new ArrayList<>();
        for (Tag tag : tags) {
            ids.add(tag.getId());
        }
        for (ProductNormsSku sku : source) {
            if (ListUtils.equals(sku.getParams(), ids))
                return sku;
        }
        return null;
    }

    /**
     * 添加tag
     *
     * @param tags
     * @param tag
     * @param maxCount 最大元素个数
     */
    public static void addTag(List<Tag> tags, Tag tag, int maxCount) {
        if (tags == null) return;
        if (tags.size() == maxCount) {
            for (Tag t : tags) {
                if (StringUtils.equals(t.getParent_id(), tag.getParent_id()))
                    tags.set(tags.indexOf(t), tag);
            }
        } else {
            tags.add(tag);
        }
    }
}
