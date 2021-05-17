package lk.nibm.lecturerportal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.util.ArrayList;

public class ViewAttendance extends AppCompatActivity {

    private static final int MAX_X_VALUE = 11;
    private static final int GROUPS = 2;
    private static final float BAR_SPACE = 0.05f;
    private static final float BAR_WIDTH = 0.3f;
    private BarChart barChart;

    ArrayList<BarEntry> values2 = new ArrayList<>();
    ArrayList<BarEntry> values1 = new ArrayList<>();

    int dse=0, dcsd=0, hdse=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_view_attendance );

        barChart = findViewById( R.id.barchart );

        Intent i = getIntent();
        dse = Integer.parseInt(i.getStringExtra( "dse" ));
        dcsd = Integer.parseInt(i.getStringExtra( "dcsd" ));
        hdse = Integer.parseInt(i.getStringExtra( "hdse" ));

        BarData data = createBarChartData();
        configureBarChartAppearance();
        prepareBarChartData(data);
    }

    private void configureBarChartAppearance() {
        barChart.setPinchZoom(false);
        barChart.setDrawBarShadow(false);
        barChart.setDrawGridBackground(false);

        barChart.getDescription().setEnabled(false);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setDrawAxisLine( false );
        xAxis.setDrawGridLines( false );

        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        leftAxis.setSpaceTop(35f);
        leftAxis.setAxisMinimum(0f);

        barChart.getAxisRight().setEnabled(false);

        barChart.getXAxis().setAxisMinimum(0);
        barChart.getXAxis().setAxisMaximum(MAX_X_VALUE);
    }

    private BarData createBarChartData() {

        values1.add( new BarEntry(1,  dse));
        values1.add( new BarEntry(2,  dcsd));
        values1.add( new BarEntry(3,  hdse));

        BarDataSet set1 = new BarDataSet(values1, "dse dcsd hdse");
        BarDataSet set2 = new BarDataSet(values2, "val2");

        set1.setColor( Color.parseColor("#FFBB86FC"));
        set2.setColor( Color.parseColor("#FFFFFF"));

        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);
        dataSets.add(set2);

        BarData data = new BarData(dataSets);
        return data;
    }

    private void prepareBarChartData(BarData data) {
        data.setDrawValues(false);
        barChart.setData(data);

        Description description = new Description();
        description.setEnabled(false);
        barChart.setDescription(description);

        barChart.getXAxis().setDrawLabels(false);

        barChart.getBarData().setBarWidth(BAR_WIDTH);

        float groupSpace = 1f - ((BAR_SPACE + BAR_WIDTH) * GROUPS);
        barChart.groupBars(0, groupSpace, BAR_SPACE);

        barChart.animateY(2000);
        barChart.notifyDataSetChanged();
        barChart.invalidate();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent( ViewAttendance.this, MainActivity.class );
        startActivity( i );
        finish();
    }
}