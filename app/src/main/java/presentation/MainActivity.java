package presentation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.warehouseinventorysystem.R;

//Main Activity:
//  Start activity of entire program
//  Immediately starts up main layout
//  Allows users to move between different pages in the app, and acts as a home page
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //Called when user taps the "View Stock" Button
    //Creates a new Intent to move activities to Stock View, and immediately moves there
    public void stockViewSwitch(View view){
        Intent stockView = new Intent(this, StockViewActivity.class);
        startActivity(stockView);
    }
<<<<<<< HEAD


    //Called when user taps the "Add Item" Button
    //Creates a new Intent to move activities to ItemAddActivity, and immediately moves there
    public void addItemSwitch(View view) {
        Intent addView = new Intent(this, ItemAddActivity.class);
        startActivity(addView);
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
            // TODO: implement messages
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
=======
>>>>>>> 11a6b7c7ba5cadfb111dcaf3c6aa8d4fbaa214b2
}