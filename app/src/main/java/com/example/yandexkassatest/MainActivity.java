package com.example.yandexkassatest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.HashSet;
import java.util.Set;

import ru.yandex.money.android.sdk.Amount;
import ru.yandex.money.android.sdk.Checkout;
import ru.yandex.money.android.sdk.PaymentMethodType;
import ru.yandex.money.android.sdk.PaymentParameters;
import ru.yandex.money.android.sdk.SavePaymentMethod;
import ru.yandex.money.android.sdk.TokenizationResult;

public class MainActivity extends AppCompatActivity {
    private Button payBtn;
    private static final int REQUEST_CODE_TOKENIZE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        payBtn = findViewById(R.id.pay_btn);
        payBtn.setOnClickListener(v->timeToStartCheckout());
    }
    void timeToStartCheckout() {
        Set<PaymentMethodType> paymentMethodTypes =  new HashSet<>();
        paymentMethodTypes.add(PaymentMethodType.BANK_CARD);
        PaymentParameters paymentParameters = new PaymentParameters(
                new Amount(BigDecimal.TEN, Currency.getInstance("RUB")),
                "Название товара",
                "Описание товара",
                "live_XXX",
                "676160",
                SavePaymentMethod.OFF,
                paymentMethodTypes
        );
        Intent intent = Checkout.createTokenizeIntent(this, paymentParameters);
        startActivityForResult(intent, REQUEST_CODE_TOKENIZE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_TOKENIZE) {
            switch (resultCode) {
                case RESULT_OK:
                    // successful tokenization
                    TokenizationResult result = Checkout.createTokenizationResult(data);
                    Toast.makeText(this, result.getPaymentToken(), Toast.LENGTH_LONG).show();

                    break;
                case RESULT_CANCELED:
                    // user canceled tokenization

                    break;
            }
        }
    }
}
