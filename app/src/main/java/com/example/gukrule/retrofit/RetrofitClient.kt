package com.example.gukrule.retrofit

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

// 싱글턴
object RetrofitClient {
    // 레트로핏 클라이언트 선언
    private var retrofitClient: Retrofit? = null

    fun initCongressRetrofit(): Retrofit {
        val url = "https://open.assembly.go.kr/portal/openapi/" //서버 주소
        val gson : Gson = GsonBuilder()
            .setLenient()
            .create()

        return Retrofit.Builder()
            .baseUrl(url)
            .client(OkHttpClient.Builder().build())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    fun getBudgetService(): BudgetApi {
        return initCongressRetrofit().create(BudgetApi::class.java)
    }

    fun initLocalRetrofit(): Retrofit {
        val url = "https://www.dorisdev.shop/" //서버 주소
        val gson = Gson()                   // 서버와 주고 받을 데이터 형식
        val clientBuilder = OkHttpClient.Builder().build()

        return Retrofit.Builder()
            .baseUrl(url)
            .client(clientBuilder)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    interface CrawlingNewsApi {
        // 정적헤더
        @Headers("content-type: application/json")
        @POST("crawling/newsList")
        fun getCrawlingNews(
            @Header("x-access-token") jwtKey: String,
            @Body() crawlingRequestData: CrawlingRequestData
        ) : Call<CrawlingNewList>
    }

    interface CrawlingArticleApi {
        @POST("crawling/article")
        fun getCrawlingArticle(
            @Header("x-access-token") jwtKey: String,
            @Body() crawlingArticleRequestData: CrawlingArticleRequestData
        ) : Call<CrawlingArticle>
    }

    interface BudgetApi {
        @GET("njzofberazvhjncha")
        fun getDetailBusiness(
            @Query("KEY") key:String = "ce1a19a050384ee5ba1d3e4fecd7d8ec", // 인증키
            @Query("Type") type:String = "json",            // 호출문서(xml,json)
//            @Query("pIndex") pIndex:Int? = null,                    // 페이지 위치
//            @Query("pSize") pSize:Int? = null,                      // 페이지 당 요청 숫자
            @Query("FSCL_YY") fsclYY:String,                // 회계년도
            @Query("EXE_DATE") exeDate:String? = null,      // 집행일
            @Query("FSCL_NM") fsclName:String? = null,      // 회계명
            @Query("FLD_NM") fldName:String? = null,        // 분야명
            @Query("SECT_NM") sectName:String? = null,      // 부문명
            @Query("PGM_NM") pgmName:String? = null,        // 프로그램명
            @Query("ACTV_NM") actvName:String? = null,      // 단위사업명
        ) : Call<BudgetResponseData>
    }

    interface AccountApi{
        @POST("/auth/check/sendSMS")
        fun postSendSms(
            @Body() accountData : AccountData
        ) : Call<SmsResponse>

        @POST("/auth/check/password")
        fun postModifyPassword(
            @Body() passwordData : PasswordData
        ) : Call<ModifyPwResponse>
    }

}