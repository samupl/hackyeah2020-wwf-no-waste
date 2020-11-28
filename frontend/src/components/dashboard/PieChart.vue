<template>
  <canvas ref="canvas"></canvas>
</template>

<script lang="ts">
import Chart from "chart.js";
import { Component, Prop, Vue, Watch } from "vue-property-decorator";

@Component
export default class PieChart extends Vue {
  @Prop({ type: Object, default: () => Chart.defaults.doughnut }) public options?:
    | object
    | undefined;
  @Prop({ default: () => [] }) readonly data!: Array<number>;
  @Prop({ default: () => [] }) readonly colors!: Array<string>;
  @Prop({ default: () => [] }) readonly labels!: Array<string>;

  mounted() {
    this.renderChart();
  }

  public renderChart() {
    this.createChart({
      datasets: [
        {
          data: this.data,
          backgroundColor: this.colors
        }
      ],
      labels: this.labels
    });
  }

  public createChart(chartData: object) {
    //const canvas = document.getElementById(`chart-${this._uid}`) as HTMLCanvasElement;
    const canvas = this.$refs.canvas as HTMLCanvasElement;
    const options = {
      type: "doughnut",
      data: chartData,
      options: this.options
    };
    new Chart(canvas, options);
  }

  @Watch("data")
  @Watch("labels")
  @Watch("colors")
  public onAnythingChanged() {
    this.renderChart();
  }
}
</script>
