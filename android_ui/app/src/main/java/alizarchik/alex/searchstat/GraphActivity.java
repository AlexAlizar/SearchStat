package alizarchik.alex.searchstat;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

/**
 * Created by aoalizarchik.
 */

public class GraphActivity extends AppCompatActivity {

    private PieChart mPieChart;
    private BarChart mBarChart;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.graph_layout);

        initGraph();

        initPieChart();
    }

    private void initPieChart() {
        mPieChart = findViewById(R.id.pie_chart);
        mPieChart.setUsePercentValues(true);

        mPieChart.setRotationAngle(0);
        mPieChart.setRotationEnabled(true);
        mPieChart.setHighlightPerTapEnabled(false);
        mPieChart.getLegend().setEnabled(false);

        setData(4, 100);
        mPieChart.setDrawHoleEnabled(false);

        mPieChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
    }

    private void setData(int count, float range) {

        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();

        entries.add(new PieEntry(10, "first"));
        entries.add(new PieEntry(20, "second"));
        entries.add(new PieEntry(30, "third"));
        entries.add(new PieEntry(40, "fourth"));

        PieDataSet dataSet = new PieDataSet(entries, "Election Results");

        ArrayList<Integer> colors = new ArrayList<>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

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

        // add a nice and smooth animation
        mBarChart.animateY(2500);

        mBarChart.getLegend().setEnabled(true);

        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0, 10, "first"));
        entries.add(new BarEntry(1, 20, "second"));
        entries.add(new BarEntry(2, 30, "third"));
        entries.add(new BarEntry(3, 40, "fourth"));

        BarDataSet d = new BarDataSet(entries, "New DataSet");
        d.setColors(ColorTemplate.VORDIPLOM_COLORS);
        d.setBarShadowColor(Color.rgb(203, 203, 203));

        ArrayList<IBarDataSet> sets = new ArrayList<>();
        sets.add(d);

        BarData cd = new BarData(sets);
        cd.setBarWidth(0.9f);


        mBarChart.setData(cd);

        mBarChart.invalidate();
    }
}
