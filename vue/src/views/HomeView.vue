<template>
  <div>
    <div style="display: flex">
      <div style="flex: 1">
        <div style="margin-bottom: 15px;font-weight:bold;font-size: 20px">系统公告</div>
        <el-collapse v-model="activeName" accordion>
          <el-collapse-item v-for="item in data" :title="item.name" :name="item.id">
            <div style="padding: 0 20px">{{ item.content }}</div>
          </el-collapse-item>
        </el-collapse>
        <el-card style="margin-top: 20px;height:475px">
          <el-calendar v-model="attendanceData.date">
            <template
                slot="dateCell"
                slot-scope="{date}">
              <span>{{ date.getDate() }}</span>
              <!-- 使用v-if避免在没有数据的时候显示解析-->
              <el-tag v-if="hasAttendance(date)" :type="computeTagType(getAttendanceStatus(date))">
                {{ getAttendanceStatus(date)}}
              </el-tag>
            </template>
          </el-calendar>
        </el-card>
      </div>
      <div style="width: 50px"></div>
      <div style="flex: 1">
        <div id="user1" style="width: 100%;height: 400px"></div>
        <div id="Employee" style="width: 100%;height: 400px"></div>
      </div>
    </div>
<!--    <div style="width: 30%">-->
<!--      <el-card style="margin-top: 20px;height:475px">-->
<!--        <el-calendar v-model="attendanceData.date">-->
<!--          <template-->
<!--              slot="dateCell"-->
<!--              slot-scope="{date}">-->
<!--            <span>{{ date.getDate() }}</span>-->
<!--            &lt;!&ndash; 使用v-if避免在没有数据的时候显示解析&ndash;&gt;-->
<!--            <el-tag v-if="hasAttendance(date)" :type="computeTagType(getAttendanceStatus(date))">-->
<!--              {{ getAttendanceStatus(date)}}-->
<!--            </el-tag>-->
<!--          </template>-->
<!--        </el-calendar>-->
<!--      </el-card>-->
<!--    </div>-->

    <!--    显示用户折线图和柱状图-->
    <!--    <div style="display: flex">-->
    <!--      <div style="flex: 1">-->
    <!--        <div id="user2" style="width: 100%;height: 400px"></div>-->
    <!--      </div>-->
    <!--      <div style="flex: 1">-->
    <!--        <div id="user3" style="width: 100%;height: 400px"></div>-->
    <!--      </div>-->
    <!--    </div>-->
  </div>
</template>

<script>

function formatDate(date) {
  const year = date.getFullYear();
  const month = String(date.getMonth() + 1).padStart(2, '0');
  const day = String(date.getDate()).padStart(2, '0');
  return `${year}-${month}-${day}`;
}

import request from "@/utils/request";
import * as echarts from 'echarts';

export default {
  name: 'HomeView',
  data() {
    return {
      attendanceData: [],
      activeName: '1',
      data: [],
    };
  },
  mounted() {
    this.findBySearch();
    this.findNotice();
    this.initUserEcharts();
    this.initEmployeeLine();
  },
  methods: {
    findNotice() {
      request.get("/notice").then(res => {
        if (res.code === '0') {
          this.data = res.data;
          this.activeName = res.data[0].id;
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    initUserEcharts() {
      request.get("/admin/echarts/pie").then(res => {
        if (res.code === '0') {
          //开始渲染数据
          this.initUserPie(res.data)
        } else {
          this.$message.error(res.msg)
        }
      })
      // request.get("/admin/echarts/bar").then(res => {
      //   if (res.code === '0') {
      //     //开始渲染数据
      //     let map=res.data
      //     this.initBar(map.xAxis,map.yAxis)
      //   } else {
      //     this.$message.error(res.msg)
      //   }
      // })
      // request.get("/admin/echarts/line").then(res => {
      //   if (res.code === '0') {
      //     //开始渲染数据
      //     let map=res.data
      //     this.initLine(map.xAxis,map.yAxis)
      //   } else {
      //     this.$message.error(res.msg)
      //   }
      // })
    },
    initUserPie(data) {
      let chartDom = document.getElementById('user1');
      let myChart = echarts.init(chartDom);
      let option;

      option = {
        title: {
          text: '人员信息统计（饼图）',
          subtext: '按部门统计',
          left: 'center'
        },
        tooltip: {
          trigger: 'item'
        },
        legend: {
          orient: 'vertical',
          left: 'left'
        },
        toolbox: {
          show: true,
          feature: {
            mark: { show: true },
            dataView: { show: true, readOnly: false },
            restore: { show: true },
            saveAsImage: { show: true }
          }
        },
        series: [
          {
            name: '人员信息',
            type: 'pie',
            radius: '50%',
            data: data,
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
              }
            }
          }
        ]
      };

      option && myChart.setOption(option);
    },
    initBar(xAxis, yAxis) {
      let chartDom = document.getElementById('user2');
      let myChart = echarts.init(chartDom);
      let option;

      option = {
        title: {
          text: '人员信息统计（柱状图）',
          subtext: '按角色统计',
          left: 'center'
        },
        xAxis: {
          type: 'category',
          data: xAxis
        },
        yAxis: {
          type: 'value'
        },
        series: [
          {
            data: yAxis,
            type: 'bar',
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
              }
            }
          }
        ]
      };
      option && myChart.setOption(option);
    },
    initLine(xAxis, yAxis) {
      let chartDom = document.getElementById('user3');
      let myChart = echarts.init(chartDom);
      let option;

      option = {
        title: {
          text: '人员信息统计（折线图）',
          subtext: '按角色统计',
          left: 'center'
        },
        xAxis: {
          type: 'category',
          data: xAxis
        },
        yAxis: {
          type: 'value'
        },
        series: [
          {
            data: yAxis,
            type: 'line',
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
              }
            }
          }
        ]
      };

      option && myChart.setOption(option);
    },
    initEmployeeLine() {
      let chartDom = document.getElementById('Employee');
      let myChart = echarts.init(chartDom);
      let option;

      option = {
        title: {
          text: '2024年员工入职情况（折线图）',
          left: 'center'
        },
        xAxis: {
          type: 'category',
          data: ['第一季度', '第二季度', '第三季度', '第四季度']
        },
        yAxis: {
          type: 'value'
        },
        series: [
          {
            data: [0,2,4,6,8,10],
            type: 'line',
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
              }
            }
          }
        ]
      };

      option && myChart.setOption(option);
    },


    //获取所有考勤数据
    findBySearch() {
      request.get("/sign/search", {
        params: this.params
      }).then(res => {
        if (res.code === "0") {
          this.attendanceData = res.data.list;
          this.total = res.data.total;
        } else {

        }
      })
    },
    computeTagType(status) {
      if (status === '正常') {
        return 'success';
      } else if(status === '旷工'){
        return 'danger';
      }else if(status === '迟到'){
        return 'warning';
      }else if(status === '早退'){
        return 'warning';
      }else if(status === '休假'){
        return 'info';
      }else if(status === '加班'){
        return '';
      }
    },

    hasAttendance(date) {
      // 检查特定日期是否有对应的考勤记录
      return this.attendanceData.some(record => record.date === formatDate(date));
    },
    getAttendanceStatus(date) {
      // 返回特定日期的考勤状态
      const record = this.attendanceData.find(record => record.date === formatDate(date));
      return record ? record.status : '';
    }
  }
}
</script>
