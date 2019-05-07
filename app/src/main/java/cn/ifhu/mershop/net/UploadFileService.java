package cn.ifhu.mershop.net;

import cn.ifhu.mershop.bean.BaseEntity;
import cn.ifhu.mershop.bean.FileModel;
import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * @author fuhongliang
 */
public interface UploadFileService {

    @Multipart
    @POST("image_upload")
    public Observable<BaseEntity<String>> imageUpload(@Part() MultipartBody.Part file );

}
