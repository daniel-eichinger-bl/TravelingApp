package prak.travelerapp.PictureAPI;

import android.os.AsyncTask;

import org.json.JSONObject;

public class GetAuthorTask extends AsyncTask<String, Void, String> {

    public AsyncPictureResponse delegate = null;
    private Exception error;

    @Override
    protected String doInBackground(String... params) {
        String data = "";
        //only searchterm given
        if(params.length == 1){
            data = ( (new PictureHTTPClient()).getImageURL(params[0]));
            //searchterm and Tag given
        }else{
            data = ( (new PictureHTTPClient()).getImageURL(params[0], params[1]));
        }

        String author = "";

        // We create out JSONObject from the data
        try {
            JSONObject jObj = new JSONObject(data);
            JSONObject firstPhoto = jObj.getJSONArray("photos").getJSONObject(0);
            JSONObject user = firstPhoto.getJSONObject("user");
            author = user.getString("fullname");
        } catch (Exception e) {
            return null;
        }
        if(!author.isEmpty()){
            return author;
        }else{
            return null;
        }

    }

    @Override
    protected void onPostExecute(String author) {
        super.onPostExecute(author);
        if(author != null) {
            delegate.getAuthorProcessFinish(author);
        }else{
            delegate.getAuthorProcessFailed();
        }
    }


}