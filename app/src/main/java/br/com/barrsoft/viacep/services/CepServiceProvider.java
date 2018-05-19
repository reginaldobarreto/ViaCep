package br.com.barrsoft.viacep.services;

import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import br.com.barrsoft.viacep.events.CepEvent;
import br.com.barrsoft.viacep.models.Cep;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CepServiceProvider {


    private static final String BASE_URL = "https://viacep.com.br/ws/";
    private Retrofit retrofit;
    private static final String TAG = CepServiceProvider.class.getName();

    private Retrofit getRetrofit() {

        if (this.retrofit == null) {
            this.retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return this.retrofit;
    }

    public void getCep(final String cep){
        //criar a interface de servico
        CepService cepService = getRetrofit().create(CepService.class);
        Call<Cep> cepData = cepService.getCep(cep);

        //consulta
        cepData.enqueue(new Callback<Cep>() {
            @Override
            public void onResponse(Call<Cep> call, Response<Cep> response) {
                Cep cep = response.body();
                String logradouro = cep.getLogradouro();
                String complemento = cep.getComplemento();
                String bairro = cep.getBairro();
                String localidade = cep.getLocalidade();
                String uf = cep.getUf();
                String unidade = cep.getUnidade();
                String ibge = cep.getIbge();

                Log.e(TAG, "Logradouro: " + logradouro);
                Log.e(TAG, "complemento: " + complemento);
                Log.e(TAG, "bairro: " + bairro);
                Log.e(TAG, "localidade: " + localidade);
                Log.e(TAG, "uf: " + uf);
                Log.e(TAG, "unidade: " + unidade);
                Log.e(TAG, "ibge: " + ibge);

                EventBus.getDefault().post(new CepEvent(cep));
            }

            @Override
            public void onFailure(Call<Cep> call, Throwable t) {

            }
        });
    }

}
