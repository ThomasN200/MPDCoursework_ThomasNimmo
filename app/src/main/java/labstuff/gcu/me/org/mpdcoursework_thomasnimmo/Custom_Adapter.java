package labstuff.gcu.me.org.mpdcoursework_thomasnimmo;

//Matriculation Number - S1625410
//Name - Thomas Nimmo

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

//Custom adapter class which allows the Listview to
//display each separate incident/planned roadwork in own container
class Custom_Adapter extends ArrayAdapter<XMLObject> {

    public Custom_Adapter(Context context, XMLObject[] incidents) {
        super(context, R.layout.custom_row ,incidents);
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater myInflater = LayoutInflater.from(getContext());
        View customView = myInflater.inflate(R.layout.custom_row, parent, false);

        XMLObject singleIncident = getItem(position);
        TextView myTextView = (TextView) customView.findViewById(R.id.txtCustom_Row);

        myTextView.setText(singleIncident.toString());

        //Passes the background colour of each row to be different than
        //the previous - makes each entry distinct from those surrounding it
        if (position % 2 == 0)
        {
            myTextView.setBackgroundColor(Color.parseColor("#82CAFA"));
        }
        else
        {
            myTextView.setBackgroundColor(Color.parseColor("#ffffff"));
        }

        return customView;
    }
}
