package com.example.kotlinproficiency1

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.io.IOException

class ApiData{
    companion object {
        const val count = 10
        val api by lazy {Connect.callApi()}
        var disposable : Disposable? = null
        fun apiData(callback:Response) : List<Fact>?{
            disposable = api.getApi(count)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    result ->
                    callback.data(result,true)
                },{error ->
                    error.printStackTrace()
                })
        return null
        }

    }
    interface Response{
        fun data(data:List<Fact>, status:Boolean) : List<Fact>?
    }
}