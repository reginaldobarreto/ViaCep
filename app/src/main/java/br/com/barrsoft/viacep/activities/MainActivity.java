package br.com.barrsoft.viacep.activities;

import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import br.com.barrsoft.viacep.R;
import br.com.barrsoft.viacep.events.CepEvent;
import br.com.barrsoft.viacep.models.Cep;
import br.com.barrsoft.viacep.services.CepService;
import br.com.barrsoft.viacep.services.CepServiceProvider;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();
    private String logradouro = "";

    @BindView(R.id.editTextCep) AppCompatEditText editTextCep;
    @BindView(R.id.buttonCep) AppCompatButton buttonConsultar;
    @BindView(R.id.textViewLogradouro) TextView textViewLogradouro;
    @BindView(R.id.textViewComplemento) TextView textViewComplemento;
    @BindView(R.id.textViewBairro) TextView textViewBairro;
    @BindView(R.id.textViewLocalidade) TextView textViewLocalidade;
    @BindView(R.id.textViewUf) TextView textViewUf;
    @BindView(R.id.textViewUnidade) TextView textViewUnidade;
    @BindView(R.id.textViewIbge) TextView textViewIbge;
    @BindView(R.id.tvLogradouro) TextView tvLogradouro;
    @BindView(R.id.tvComplemento) TextView tvComplemento;
    @BindView(R.id.tvBairro) TextView tvBairro;
    @BindView(R.id.tvLocalidade) TextView tvLocalidade;
    @BindView(R.id.tvUf) TextView tvUf;
    @BindView(R.id.tvUnidade) TextView tvUnidade;
    @BindView(R.id.tvIbge) TextView tvIbge;
    @BindView(R.id.linearComplemento) LinearLayout linearComplemento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        buttonConsultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String consulta = editTextCep.getText().toString();
                if (consulta.isEmpty() || consulta.length() < 8 ){
                    Toast.makeText(getApplicationContext(),"Preencha o CEP corretamente",Toast.LENGTH_LONG).show();
                }else{
                    requestCep(consulta + "/json/");
                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCepEvent(CepEvent cepEvent){
        String logradouro = cepEvent.getCep().getLogradouro();
        String complemento = cepEvent.getCep().getComplemento();
        String bairro = cepEvent.getCep().getBairro();
        String localidade = cepEvent.getCep().getLocalidade();
        String uf = cepEvent.getCep().getUf();
        String unidade = cepEvent.getCep().getUnidade();
        String ibge = cepEvent.getCep().getIbge();

        textViewLogradouro.setText(logradouro);
        textViewComplemento.setText(complemento);
        textViewBairro.setText(bairro);
        textViewLocalidade.setText(localidade);
        textViewUf.setText(uf);
        textViewUnidade.setText(unidade);
        textViewIbge.setText(ibge);

        if (logradouro == null){
            Toast.makeText(getApplicationContext(),"CEP não localizado",Toast.LENGTH_LONG).show();
        }

        else{
            tvLogradouro.setVisibility(View.VISIBLE);
            tvComplemento.setVisibility(View.VISIBLE);
            tvBairro.setVisibility(View.VISIBLE);
            tvLocalidade.setVisibility(View.VISIBLE);
            tvUf.setVisibility(View.VISIBLE);
            tvUnidade.setVisibility(View.VISIBLE);
            tvIbge.setVisibility(View.VISIBLE);
        }
    }

    private void requestCep(String consultaFinal) {
        CepServiceProvider cepServiceProvider  = new CepServiceProvider();
        cepServiceProvider.getCep(consultaFinal);
    }
}
