package com.wdl.face;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.Spannable;
import android.util.ArrayMap;
import android.view.View;

import net.qiujuer.genius.res.Resource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/**
 * author：   wdl
 * time： 2018/9/18 16:49
 * des：    表情工具类
 */
public class Face {

    //全局的表情映射 轻量级
    private static final ArrayMap<String,Bean> FACE_MAP = new ArrayMap<>();
    private static List<FaceTab> FACE_TABS = null;
    private static void init(Context context){
        if (FACE_TABS==null){
            synchronized (Face.class){
                if (FACE_TABS==null){
                    ArrayList<FaceTab> faceTabs = new ArrayList<>();
                    //drawable中拉取
                    FaceTab tab = initResources(context);
                    if (tab!=null)faceTabs.add(tab);
                    //assert中拉取
                    tab = initAssertsFace(context);
                    if (tab!=null)faceTabs.add(tab);
                    //init map
                    for (FaceTab faceTab : faceTabs) {
                        faceTab.copyToMap(FACE_MAP);
                    }

                    //init list 不可变的集合
                    FACE_TABS = Collections.unmodifiableList(faceTabs);
                }
            }
        }
    }

    //assert中拉取
    private static FaceTab initAssertsFace(Context context) {
        return null;
    }

    //drawable中拉取并映射到对应的key
    private static FaceTab initResources(Context context) {
        final ArrayList<Bean> faces = new ArrayList<>();
        final Resources resources = context.getResources();
        String packageName = context.getApplicationInfo().packageName;
        for (int i = 0;i<=142;i++){
            //i=0 001
            String key = String.format(Locale.ENGLISH,"fb%03d",i);
            String resStr = String.format(Locale.ENGLISH,"face_base_%03d",i);
            //根据资源名拿资源对应的id
            int resId = resources.getIdentifier(resStr,"drawable",packageName);
            if (resId==0)continue;
            //添加表情
            faces.add(new Bean(key,resId));
        }
        if (faces.size()==0)
            return null;

        return new FaceTab(faces,"NAME",faces.get(0).preview);
    }

    /**
     * 获取所有表情
     * @param context
     * @return
     */
    public static List<FaceTab> all(@NonNull Context context){
        init(context);
        return FACE_TABS;
    }

    /**
     * 输入表情到edit
     * @param context 上下文
     * @param editable Editable
     * @param bean Face.Bean
     * @param size 大小
     */
    public static void inputFace(@NonNull Context context, final Editable editable,
                                          final Face.Bean bean,final int size){

    }

    /**
     * 从spannable解析表情并替换
     * @param target
     * @param spannable Spannable
     * @param size 大小
     * @return
     */
    public static List<FaceTab> decode(@NonNull View target, final Spannable spannable,
                                       final int size){
        return null;
    }

    /**
     * 表情盘,含多个表情
     */
    public static class FaceTab{
        public List<Bean> faces = new ArrayList<>();
        public String name;
        //预览图，Object 因为  drawable 下的表情资源是以id int 形式存在的
        public Object preview;

        public FaceTab(List<Bean> faces, String name, Object preview) {
            this.faces = faces;
            this.name = name;
            this.preview = preview;
        }

        public void copyToMap(ArrayMap<String,Bean> faceMap){
            for (Bean face : faces) {
                faceMap.put(face.key,face);
            }
        }
    }

    /**
     * 每个表情
     */
    public static class Bean{
        public static String key;
        public static Object source;
        public static Object preview;
        public static String desc;

        public Bean(String key,int preview) {
            this.key = key;
            this.source = preview;
            this.preview = preview;
        }
    }
}
