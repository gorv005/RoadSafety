package com.app.roadsafety.frameworks.retrofit;

import com.google.gson.Gson;

import org.apache.http.conn.ConnectTimeoutException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import javax.net.ssl.SSLHandshakeException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class ResponseResolver<T> implements Callback<T> {

    String NO_INTERNET_MESSAGE = "Please check internet connectivity status";
    String REMOTE_SERVER_FAILED_MESSAGE = "Remote server failed to respond";
    String CONNECTION_TIME_OUT_MESSAGE = "Connection timed out";
    String SSL_HANDSHAKE_FAILED = "SSL handshake failed";
    String CONNECTION_REFUSED = "Connection was refused from server";

    public abstract void onSuccess(T t, Response response);

    public abstract void onFailure(RestError error, String msg);

    private RestError resolveNetworkError(Throwable cause) {
        RestError error = new RestError();
        if (cause instanceof UnknownHostException) {
            error.setHttpCode(1000);
            error.setMessage(NO_INTERNET_MESSAGE);
        } else if (cause instanceof SocketTimeoutException) {
            error.setHttpCode(1001);
            error.setMessage(REMOTE_SERVER_FAILED_MESSAGE);
        } else if (cause instanceof ConnectTimeoutException) {
            error.setHttpCode(1002);
            error.setMessage(CONNECTION_TIME_OUT_MESSAGE);
        } else if (cause instanceof SSLHandshakeException) {
            error.setHttpCode(1003);
            error.setMessage(SSL_HANDSHAKE_FAILED);
        } else if (cause instanceof ConnectException) {
            error.setHttpCode(1004);
            error.setMessage(CONNECTION_REFUSED);
        } else {
            error.setHttpCode(1005);
            error.setMessage(cause.getMessage());

        }
        return error;
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (response.isSuccessful()) {
            T blossomResponse = response.body();
            onSuccess(blossomResponse, response);
        } else {
            RestError body = new RestError();
           // body.setHttpCode(response.code());
           // body=(RestError)response.errorBody();
            try {
                Gson gson=new Gson();
               // RestError restError= gson.fromJson(response.errorBody().string(),RestError.class);
                String m=response.errorBody().string();
                while (m.contains("[]") || m.contains("{}")) {
                    m = m.replace("[]", "null");
                    m = m.replace("{}", "null");
                }
                    onFailure(null, m);

            } catch (Exception e) {
                e.printStackTrace();
               /* try {
                    onFailure(null, response.errorBody().string());
                } catch (Exception e1) {
                    e1.printStackTrace();
                }*/

            }

        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        t.printStackTrace();
        RestError body = resolveNetworkError(t);

        onFailure(body, body.getMessage());
    }
}
