package com.sjxz.newsclient.rx.api;

import com.sjxz.newsclient.bean.news.JsonRootBean;

import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import rx.Observable;

/**
 * @author WYH_Healer
 * @email 3425934925@qq.com
 * Created by xz on 2017/3/13.
 * Role:
 */
public interface NewsListService {

    String BASE_URL = ApiInterface.NETEAST_HOST;

    @GET("nc/article/{type}/{id}/{startPage}-20.html")
    Observable<JsonRootBean> getNewsList(
            @Header("Cache-Control") String cacheControl,
            @Path("type") String type, @Path("id") String id,
            @Path("startPage") int startPage);
}
