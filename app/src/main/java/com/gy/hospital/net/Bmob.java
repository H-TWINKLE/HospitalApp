package com.gy.hospital.net;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.gy.hospital.R;
import com.gy.hospital.entity.User;
import com.gy.hospital.entity.WelPic;
import com.gy.hospital.entity.notice;
import com.gy.hospital.entity.opinion;
import com.gy.hospital.entity.post;
import com.gy.hospital.entity.register;
import com.gy.hospital.entity.seek;
import com.gy.hospital.utils.Constants;
import com.gy.hospital.utils.Utils;

import org.xutils.common.Callback;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FetchUserInfoListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

public enum Bmob {

    INSTANCE;

    private static final String TAG = "Bmob";

    private int getDefaultPages(int pages) {
        return pages == 0 ? 1 : pages;
    }


    private <T> void setReturnInterfaceValue(T t, Exception e) {
        if (e == null) {
            if (bmobOnGetList != null) {
                bmobOnGetList.onbmobOnGetListSuccess(t);
            }
        } else {
            if (bmobOnGetList != null) {
                Log.i(TAG, "setReturnInterfaceValue: " + e);
                bmobOnGetList.onbmobOnGetListFailure(e.getLocalizedMessage());
            }
        }
    }

    public void getWelcomePic(int pages) {

        BmobQuery<WelPic> query = new BmobQuery<>();
        query.order("-createdAt");
        query.setLimit(10);
        query.setSkip(pages * 10);
        query.include("hospital");

        query.findObjects(new FindListener<WelPic>() {
            @Override
            public void done(List<WelPic> list, BmobException e) {
                setReturnInterfaceValue(list, e);
            }
        });

    }

    public void getGuahao(int pages) {

        BmobQuery<register> query = new BmobQuery<>("register");
        query.order("-createdAt");
        query.setLimit(10);
        query.setSkip(pages * 10);
        query.include("department,user");
        query.addWhereEqualTo("user", BmobUser.getCurrentUser(User.class).getObjectId());

        query.findObjects(new FindListener<register>() {
            @Override
            public void done(List<register> list, BmobException e) {
                setReturnInterfaceValue(list, e);
            }
        });

    }

    public void getNotice(int pages) {

        BmobQuery<notice> query = new BmobQuery<>();
        query.order("-createdAt");
        query.setLimit(10);
        query.setSkip(pages * 10);
        query.include("author");

        query.findObjects(new FindListener<notice>() {
            @Override
            public void done(List<notice> list, BmobException e) {
                setReturnInterfaceValue(list, e);
            }
        });

    }

    public void getPost(int pages) {

        BmobQuery<post> query = new BmobQuery<>();
        query.order("-createdAt");
        query.setLimit(10);
        query.setSkip(pages * 10);
        query.include("author");

        query.findObjects(new FindListener<post>() {
            @Override
            public void done(List<post> list, BmobException e) {
                setReturnInterfaceValue(list, e);
            }
        });

    }

    public void getSeek(int pages) {

        BmobQuery<seek> query = new BmobQuery<>();
        query.order("-createdAt");
        query.setLimit(10);
        query.setSkip(pages * 10);
        query.include("user,doctor");

        query.findObjects(new FindListener<seek>() {
            @Override
            public void done(List<seek> list, BmobException e) {
                setReturnInterfaceValue(list, e);
            }
        });

    }

    public void getOpinion(int pages) {

        BmobQuery<opinion> query = new BmobQuery<>();
        query.order("-createdAt");
        query.setLimit(10);
        query.setSkip(pages * 10);
        query.include("admin");
        query.addWhereEqualTo("user", BmobUser.getCurrentUser(User.class).getObjectId());

        query.findObjects(new FindListener<opinion>() {
            @Override
            public void done(List<opinion> list, BmobException e) {
                setReturnInterfaceValue(list, e);
            }
        });

    }

    public <T extends BmobObject> void toSave(T t) {
        t.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                setReturnInterfaceValue(s, e);
            }
        });
    }


    public void toRegister(User u) {

        u.signUp(new SaveListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                setReturnInterfaceValue(user, e);
            }
        });

    }

    //修改密码没有登录，旧密码新密码
    public void BmobModifyPassWithoutLogin(User user, String pass, Context c) {


        RequestParams r = new RequestParams(Constants.UPDATEUSERPASSINNOTLOGIN + user.getObjectId());

        r.addHeader("Content-Type", "application/json");
        r.addHeader("X-Bmob-REST-API-Key", c.getString(R.string.BMOB_REST_KEY));
        r.addHeader("X-Bmob-Master-Key", c.getString(R.string.BMOB_MASTER_KEY));
        r.addHeader("X-Bmob-Application-Id", c.getString(R.string.BMOB_APP_KEY));

        JSONObject js = new JSONObject();

        js.put("oldPassword", user.getPass());
        js.put("newPassword", pass);

        r.setBodyContent(js.toJSONString());

        x.http().request(HttpMethod.PUT, r, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {


                if (Utils.INSTANCE.verificationBmobMsgIsOk(result)) {
                    User u = new User();
                    u.setPass(pass);
                    u.setObjectId(user.getObjectId());
                    BmobModifyPassWithoutLoginByPass2(u, c);
                } else {
                    if (modifyPassWithoutLoginListener != null) {
                        modifyPassWithoutLoginListener.ModifyPassWithoutLoginFailure("修改失败");
                    }
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                if (modifyPassWithoutLoginListener != null) {
                    modifyPassWithoutLoginListener.ModifyPassWithoutLoginFailure(ex.getLocalizedMessage());
                }
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });

    }

    //修改个人信息通过masterkey
    private void BmobModifyPassWithoutLoginByPass2(User user, Context c) {

        RequestParams r = new RequestParams(Constants.UPDATEUSERPASSINNOTLOGINBYPASS2 + user.getObjectId());

        r.addHeader("Content-Type", "application/json");
        r.addHeader("X-Bmob-REST-API-Key", c.getString(R.string.BMOB_REST_KEY));
        r.addHeader("X-Bmob-Master-Key", c.getString(R.string.BMOB_MASTER_KEY));
        r.addHeader("X-Bmob-Application-Id", c.getString(R.string.BMOB_APP_KEY));

        JSONObject js = new JSONObject();

        js.put("pass", user.getPass());

        r.setBodyContent(js.toJSONString());

        x.http().request(HttpMethod.PUT, r, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

                JSONObject js = JSONObject.parseObject(result);

                if (js.get("updatedAt") != null) {
                    if (modifyPassWithoutLoginListener != null) {
                        modifyPassWithoutLoginListener.ModifyPassWithoutLoginSuccess();
                    }
                } else {
                    if (modifyPassWithoutLoginListener != null) {
                        modifyPassWithoutLoginListener.ModifyPassWithoutLoginFailure("修改失败");
                    }
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                if (modifyPassWithoutLoginListener != null) {
                    modifyPassWithoutLoginListener.ModifyPassWithoutLoginFailure(ex.getLocalizedMessage());
                }
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });


    }

    public interface ModifyPassWithoutLoginListener {
        void ModifyPassWithoutLoginSuccess();

        void ModifyPassWithoutLoginFailure(String text);
    }

    private ModifyPassWithoutLoginListener modifyPassWithoutLoginListener;

    public void setModifyPassWithoutLoginListener(ModifyPassWithoutLoginListener modifyPassWithoutLoginListener) {
        this.modifyPassWithoutLoginListener = modifyPassWithoutLoginListener;
    }


    public void toLogin(String username, String pass) {

        User u = new User();

        u.setUsername(username);
        u.setPassword(pass);

        u.login(new SaveListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                setReturnInterfaceValue(user, e);
            }
        });

    }

    public void findUserByTel(String tel) {
        BmobQuery<User> query = new BmobQuery<>();
        query.addWhereEqualTo("username", tel);
        query.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> object, BmobException e) {

                if (object == null || object.size() == 0) {
                    object = new ArrayList<>();
                    object.add(new User());
                }

                setReturnInterfaceValue(object.get(0), e);


            }
        });
    }

    //上传单张图片
    public void BmobUploadPic(File file) {
        final BmobFile bmobFile = new BmobFile(file);
        bmobFile.uploadblock(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    User u = new User();
                    u.setHeaderPic(bmobFile.getFileUrl());

                    BmobModifyUserInfo(u);
                } else {

                    if (bmobOnGetList != null) {
                        bmobOnGetList.onbmobOnGetListFailure(e.getMessage());
                    }

                }
            }

            @Override
            public void onProgress(Integer value) {
                // 返回的上传进度（百分比）
            }
        });
    }


    //修改用户个人信息 通过登录
    public void BmobModifyUserInfo(User u) {
        u.update(BmobUser.getCurrentUser(User.class).getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                setReturnInterfaceValue(u, e);
            }
        });
    }

    //同步个人信息
    public void BmobFetchUserInfo(Context context) {
        BmobUser.fetchUserJsonInfo(new FetchUserInfoListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    Toast.makeText(context, "同步个人信息成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "同步个人信息失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    public interface bmobOnGetList<T> {
        void onbmobOnGetListSuccess(T t);

        void onbmobOnGetListFailure(String text);
    }

    private bmobOnGetList bmobOnGetList;

    public void setBmobOnGetList(Bmob.bmobOnGetList bmobOnGetList) {
        this.bmobOnGetList = bmobOnGetList;
    }


}
