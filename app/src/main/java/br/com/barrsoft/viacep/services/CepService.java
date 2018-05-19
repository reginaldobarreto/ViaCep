package br.com.barrsoft.viacep.services;

import br.com.barrsoft.viacep.models.Cep;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CepService {
    @GET("{cep}")
    Call<Cep> getCep(@Path("cep") String cep);
}