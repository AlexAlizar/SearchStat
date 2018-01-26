package alizarchik.alex.searchstat;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import alizarchik.alex.searchstat.model.DailyStatisticsModel;
import alizarchik.alex.searchstat.model.GenStatDataItem;

/**
 * Created by aoalizarchik.
 */

public class GraphActivity extends AppCompatActivity {

    private PieChart mPieChart;
    private BarChart mBarChart;
    ArrayList<Integer> colors = new ArrayList<Integer>();
    private ArrayList<GenStatDataItem> listGS;
    private ArrayList<DailyStatisticsModel> listDS;
    boolean general = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.graph_layout);
        String activity = getIntent().getStringExtra("activity");
        if (activity.equals("general")) {
            general = true;
        } else {
            general = false;
        }
        Log.i("TAG", "general = " + general);
        listGS = (ArrayList<GenStatDataItem>) getIntent().getSerializableExtra("dataGeneral");
        listDS = (ArrayList<DailyStatisticsModel>) getIntent().getSerializableExtra("dataDaily");

        initColorPalette();

        initGraph();

        initPieChart();
    }

    private void initColorPalette() {
        colors.add(Color.rgb(1, 87, 150));
        colors.add(Color.LTGRAY);
        colors.add(Color.rgb(87, 149, 198));
        colors.add(Color.rgb(127, 68, 179));
        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);


    }

    private void initPieChart() {
        mPieChart = findViewById(R.id.pie_chart);
        mPieChart.setUsePercentValues(true);

        mPieChart.setRotationAngle(0);
        mPieChart.setRotationEnabled(true);
        mPieChart.setHighlightPerTapEnabled(false);
        mPieChart.getLegend().setEnabled(false);
        mPieChart.setEntryLabelColor(Color.BLACK);
        mPieChart.getDescription().setEnabled(false);

        setData();
        mPieChart.setDrawHoleEnabled(false);

        mPieChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
    }

    private void setData() {

        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();

        int max = 0;

        if (general) {

            for (int i = 0; i < listGS.size(); i++) {
                max += listGS.get(i).getRank();
            }

            for (int i = 0; i < listGS.size(); i++) {
                entries.add(new PieEntry(listGS.get(i).getRank() * 100 / max, listGS.get(i).getName()));
            }
        } else {
            for (int i = 0; i < listDS.size(); i++) {
                max += listDS.get(i).getCountOfPages();
            }

            for (int i = 0; i < listDS.size(); i++) {
                SimpleDateFormat sdfIn = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                SimpleDateFormat sdfOut = new SimpleDateFormat("dd-MM-yyyy");
                String input = listDS.get(i).getDate();
                Date date = null;
                try {
                    date = sdfIn.parse(input);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                String outputText = sdfOut.format(date);
                entries.add(new PieEntry(listDS.get(i).getCountOfPages() * 100 / max, outputText));
            }
        }


        PieDataSet dataSet = new PieDataSet(entries, "Election Results");
        dataSet.setColors(colors);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.BLACK);
        mPieChart.setData(data);

        mPieChart.invalidate();
    }

    private void initGraph() {

        mBarChart = (BarChart) findViewById(R.id.graph_with_statistics);

        mBarChart.getDescription().setEnabled(false);
        mBarChart.setMaxVisibleValueCount(10);
        mBarChart.setPinchZoom(false);
        mBarChart.setDrawBarShadow(false);
        mBarChart.setDrawGridBackground(false);
        XAxis xAxis = mBarChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setDrawLabels(true);
        xAxis.setDrawGridLines(false);


        YAxis rightAxis = mBarChart.getAxisRight();
        rightAxis.setDrawLabels(false);

        // add a nice and smooth animation
        mBarChart.animateY(2500);

        mBarChart.getLegend().setEnabled(false);

        ArrayList<BarEntry> entries = new ArrayList<>();

        if (general) {
            for (int i = 0; i < listGS.size(); i++) {
                entries.add(new BarEntry(i + 1, listGS.get(i).getRank(), listGS.get(i).getName()));
            }
        } else {
            for (int i = 0; i < listDS.size(); i++) {
                SimpleDateFormat sdfIn = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                SimpleDateFormat sdfOut = new SimpleDateFormat("dd-MM-yyyy");
                String input = listDS.get(i).getDate();
                Date date = null;
                try {
                    date = sdfIn.parse(input);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                String outputText = sdfOut.format(date);
                entries.add(new BarEntry(i + 1, listDS.get(i).getCountOfPages(), outputText));
            }
        }

        BarDataSet d = new BarDataSet(entries, "New DataSet");
        d.setColors(colors);
        d.setBarShadowColor(Color.rgb(203, 203, 203));

        ArrayList<IBarDataSet> sets = new ArrayList<>();
        sets.add(d);

        BarData cd = new BarData(sets);
        cd.setValueTextColor(Color.BLACK);
        cd.setBarWidth(0.9f);


        mBarChart.setData(cd);

        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                int pos = (int) value - 1;
                return entries.get(pos).getData().toString();
            }
        });

        mBarChart.invalidate();
    }
}
