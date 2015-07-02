package LTournament.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.http.client.*;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Entry point classes define <code>onModuleLoad()</code>
 */
public class LTournament implements EntryPoint {

    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {
        GUI.assembleStartUp();
        RootLayoutPanel rp = RootLayoutPanel.get();
        rp.add(GUI.dockLayoutPanel);
    }

    /**
     * No clue what any of this means. IDEA must have created this.
     */
    private static class MyAsyncCallback implements AsyncCallback<String> {
        private Label label;

        public MyAsyncCallback(Label label) {
            this.label = label;
        }

        public void onSuccess(String result) {
            label.getElement().setInnerHTML(result);
        }

        public void onFailure(Throwable throwable) {
            label.setText("Failed to receive answer from server!");
        }
    }

/*


*/





}