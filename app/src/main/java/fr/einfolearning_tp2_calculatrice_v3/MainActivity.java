package fr.einfolearning_tp2_calculatrice_v3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by B. LEMAIRE on 28/02/2017.
 */

public class MainActivity extends Activity {

    private final static String KEY_BUNDLE_LISTE_OPERATIONS="liste_operations";
    // Clefs pour les extras de de l'Intent
    public final static String OPERANDE_1 = "operande_1";
    public final static String OPERANDE_2 = "operande_2";
    public final static String OPERATEUR = "operateur";

    private EditText operande_1;    // Zone d'édition pour l'operande 1
    private EditText operande_2;    // Zone d'édition pour l'operande 2
    private EditText operateur;     // Zone d'édition pour l'operateur

    private ArrayList<String> liste_operations = new ArrayList<String>();

    private Button calculer; // Bouton de calcul

    // Affichage du résultat du calcul
    private TextView tv_resultat;

    // Code de requête pour le retour du résultat de l'activité calcul
    public static final int REQ_CALCUL = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Désérialisation du layout
        setContentView(R.layout.activity_main);

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
        operande_1 = (EditText) findViewById(R.id.et_op1);
        operande_2 = (EditText) findViewById(R.id.et_op2);
        operateur = (EditText) findViewById(R.id.et_oper);
        tv_resultat = (TextView) findViewById(R.id.tv_resultat);

        calculer = (Button) findViewById(R.id.bt_calculer);
    }

    /**
     * Mise en place des écouteurs
     */
    private void initConnection() {
        /** Dans l'écouteur il faut :
         *
         * - Instancier un Intent explicite
         * - Charger les extras pour le calcul
         * - Lancer l'activité de calcul
         */

        calculer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s_oper_1 = operande_1.getText().toString();
                String s_oper_2 = operande_2.getText().toString();
                String s_oper = operateur.getText().toString();

                /**
                 * Préparation de l'Intent pour le lancement de l'activité pour
                 * le calcul
                 */
                Intent intent = new Intent(MainActivity.this, Calcul.class);

                /**
                 * Chargement des extras pour le calcul
                 */
                intent.putExtra(MainActivity.OPERANDE_1, s_oper_1);
                intent.putExtra(MainActivity.OPERANDE_2, s_oper_2);
                intent.putExtra(MainActivity.OPERATEUR, s_oper);

                /**
                 * Lancement de l'activité de calcul
                 */
                /**
                 * Lancement de l'activité de calcul
                 */
                startActivityForResult(intent, MainActivity.REQ_CALCUL);

                Log.i("MainActivity", "MainAcitivty-->Calcul");
            }
        });
    }

    private void display(){

        String s_affichage="";
        for (String s : this.liste_operations)
            s_affichage+=s+'\n';

        tv_resultat.setText(s_affichage);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        int i = 0;

        if (requestCode == MainActivity.REQ_CALCUL && resultCode == Activity.RESULT_OK) {
            String s_resultat = data.getStringExtra(Calcul.RES_CALCUL);
            this.liste_operations.add(s_resultat);
            this.display();
        } else
            super.onActivityResult(requestCode, resultCode, data);
    }



    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putStringArrayList(MainActivity.KEY_BUNDLE_LISTE_OPERATIONS, this.liste_operations);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        this.liste_operations = savedInstanceState.getStringArrayList(MainActivity.KEY_BUNDLE_LISTE_OPERATIONS);
        this.display();

    }
}
