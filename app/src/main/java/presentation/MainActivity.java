package presentation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.warehouseinventorysystem.R;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import database.DatabaseManager;
import logic.AccountAccessor;

//Main Activity:
//  Start activity of entire program
//  Immediately starts up main layout
//  Allows users to move between different pages in the app, and acts as a home page
public class MainActivity extends AppCompatActivity {
    private static boolean isFirstCall = false;
    AccountAccessor accounts;
    EditText username;
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Gets database on device and grabs account database
        if (!isFirstCall)
        {
            copyDatabaseToDevice();
            isFirstCall = true;
        }
            accounts = new AccountAccessor();
    }

    //Checks if the username and password are valid, if so logs user in and switches to the main menu
    //Otherwise an error message is shown to the user
    public void logInSwitch(View view){
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);

        //If username and password are correct
        if(accounts.verifyLogin(username.getText().toString(), password.getText().toString())){
            Intent main = new Intent(this, InventoryViewActivity.class);
            startActivity(main);
        }

        //If username or password are incorrect
        else {
           Messages.incorrectLogin(this, "Please try a correct username and password");
        }
    }

    //Moves to the create account page on click
    public void createAccountSwitch(View view){
        Intent create = new Intent(this, CreateAccountActivity.class);
        startActivity(create);
    }

    /* code based off of provided sample-project by instructors */
    private void copyDatabaseToDevice() {
        final String DB_PATH = "db";

        String[] assetNames;
        Context context = getApplicationContext();
        File dataDirectory = context.getDir(DB_PATH, Context.MODE_PRIVATE);
        AssetManager assetManager = getAssets();

        try {
            DatabaseManager dbManager = DatabaseManager.getInstance();
            assetNames = assetManager.list(DB_PATH);
            for (int i = 0; i < assetNames.length; i++) {
                assetNames[i] = DB_PATH + "/" + assetNames[i];
            }

            copyAssetsToDirectory(assetNames, dataDirectory);

            dbManager.setDBFilePath(dataDirectory.toString() + "/" + dbManager.getDBFilePath());

        } catch (final IOException ioe) {
            //Messages.warning(this, "Unable to access application data: " + ioe.getMessage());
        }
    }

    /* code based off of provided sample-project by instructors */
    public void copyAssetsToDirectory(String[] assets, File directory) throws IOException {
        AssetManager assetManager = getAssets();

        for (String asset : assets) {
            String[] components = asset.split("/");
            String copyPath = directory.toString() + "/" + components[components.length - 1];

            char[] buffer = new char[1024];
            int count;

            File outFile = new File(copyPath);

            if (!outFile.exists()) {
                InputStreamReader in = new InputStreamReader(assetManager.open(asset));
                FileWriter out = new FileWriter(outFile);

                count = in.read(buffer);
                while (count != -1) {
                    out.write(buffer, 0, count);
                    count = in.read(buffer);
                }

                out.close();
                in.close();
            }
        }
    }
}