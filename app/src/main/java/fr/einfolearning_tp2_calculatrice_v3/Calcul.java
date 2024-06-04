package fr.einfolearning_tp2_calculatrice_v3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


/**
 * Created by B. LEMAIRE on 28/02/2017.
 */

    public class Calcul extends Activity {

    public static final String RES_CALCUL = "resultat";
    private TextView tv_calcul;
    private String expression = "";
    private Button retour;  // Bouton pour retourner à l'activité principale

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Désérialisation du layout
        setContentView(R.layout.layout_calcul);

        /**
         * Désérialisation des ressources
         */
        this.deserialiserRessources();

        /**
         * Mise en place de l'écouteur du bouton calculer
         */
        this.initConnection();

    }


    /**
     * Désérialise les ressources
     */
    private void deserialiserRessources() {

        /**
         * Désérialisation des ressources
         */
        tv_calcul = (TextView) findViewById(R.id.tv_res);
        retour = (Button) findViewById(R.id.bt_retour);

    }

    /**
     * Mise en place des écouteurs
     */
    private void initConnection() {

        // Mise en place de l'écouteur du bouton calculer
        retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retour du résultat à l'activité appelante
                Calcul.this.prepareResult(expression);
            }
        });

        // Récupération de l'Intent qui a servi à lancer cette activité
        Intent intent = getIntent();

        // Récupération des extras
        String s_oper_1 = intent.getStringExtra(MainActivity.OPERANDE_1);
        String s_oper_2 = intent.getStringExtra(MainActivity.OPERANDE_2);
        String s_oper = intent.getStringExtra(MainActivity.OPERATEUR);

        // Calcul du résultat
        expression = s_oper_1 + s_oper + s_oper_2 + " = ";

        if (!s_oper.equals("+")
                && !s_oper.equals("-")
                && !s_oper.equals("/")
                && !s_oper.equals("*")
        )
            expression += "Erreur";
        else {
            try {
                double oper1 = Double.parseDouble(s_oper_1);
                double oper2 = Double.parseDouble(s_oper_2);

                double res;
                if (s_oper.equals("+")) {
                    res = oper1 + oper2;
                } else if (s_oper.equals("-")) {
                    res = oper1 - oper2;
                } else if (s_oper.equals("/")) {
                    if (oper2 == 0) throw new ArithmeticException();

                    res = oper1 / oper2;
                } else res = oper1 * oper2;

                expression += " " + res;
            } catch (ArithmeticException e) {
                expression += "Division par 0 !";
            } catch (NumberFormatException e) {
                expression += "Erreur";
            }
        }

        tv_calcul.setText(expression);

    }


    /**
     * Prépare et retourne le résultat à l'activité principale
     * @param expression  (String) : résultat du calcul
     */
    private void prepareResult(String expression){
        // Intent qui contiendra le résultat
        Intent intentRes = new Intent();

        // Chargement du résultat dans l'intent et retour du résultat
        intentRes.putExtra(Calcul.RES_CALCUL,expression);
        setResult(Calcul.RESULT_OK, intentRes);

        // L'activité se termine
        finish();
    }
}
