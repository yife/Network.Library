package info.yife.network.library;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class CheckNetworkConnection {
	Context context;
	
	public CheckNetworkConnection(Context context){
		this.context = context;
	}
	
	public Boolean isConnected(){
		ConnectivityManager connManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connManager.getActiveNetworkInfo();
		if(networkInfo != null && networkInfo.isConnected()){
			return true;
		}else{
			return false;
		}
	}
}
