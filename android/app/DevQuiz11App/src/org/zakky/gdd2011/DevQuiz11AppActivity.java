
package org.zakky.gdd2011;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.apps.gddquiz.IQuizService;

public class DevQuiz11AppActivity extends Activity implements ServiceConnection {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Intent intent = new Intent(IQuizService.class.getName());
        bindService(intent, this, BIND_AUTO_CREATE);
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        final IQuizService quizService = IQuizService.Stub.asInterface(service);
        try {
            final String code = quizService.getCode();
            ((TextView) findViewById(R.id.text)).setText(code);
        } catch (RemoteException e) {
            Toast.makeText(this, "error", Toast.LENGTH_LONG);
        }
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
    }
}
