package com.dyshkotaras.todolist;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class WebViewModel extends AndroidViewModel {
    public WebViewModel(@NonNull Application application) {
        super(application);

    }

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    private MutableLiveData<Boolean> isError = new MutableLiveData<>();
    private MutableLiveData<Boolean> isInternet = new MutableLiveData<>();

    public LiveData<Boolean> getIsError() {
        return isError;
    }

    public MutableLiveData<Boolean> getIsInternet() {
        return isInternet;
    }

    public void isConnectedToInternet(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
            isInternet.setValue(activeNetwork != null && activeNetwork.isConnected());
        }
    }

    public void executeApiRequest() {
        ApiRequest apiRequest = new ApiRequest();
        Disposable disposable = apiRequest.makeApiRequest()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean is404) throws Throwable {
                        isError.setValue(is404);
                    }
                });

        compositeDisposable.add(disposable);
    }


    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }
}
