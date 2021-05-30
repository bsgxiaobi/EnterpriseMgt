<template>
	<div id="attendance-main">
		<el-tabs v-model="activeName" @tab-click="handleClick">
		    <el-tab-pane label="考勤管理" name="first">
				<LateEarly></LateEarly>
			</el-tab-pane>
		    <el-tab-pane label="请假管理" name="second">
				<ApplyLeave></ApplyLeave>
			</el-tab-pane>
		    <el-tab-pane label="出差管理" name="third">
				<BusinessTrip></BusinessTrip>
			</el-tab-pane>
			<el-tab-pane label="加班管理" name="four">
				<Overtime></Overtime>
			</el-tab-pane>
		</el-tabs>
		
	</div>
</template>

<script>


import LateEarly from '@/components/Personal/Attendance/LateEarly.vue';
import ApplyLeave from '@/components/Personal/Attendance/ApplyLeave.vue';
import BusinessTrip from '@/components/Personal/Attendance/BusinessTrip.vue';
import Overtime from '@/components/Personal/Attendance/Overtime.vue';
export default{
	name:'Attendance',
	components:{
		LateEarly,ApplyLeave,BusinessTrip,Overtime
	},
	data(){
		return{
			activeName:'first',
		}
	}
}
</script>

<style scoped>
@import url("../../assets/css/Personal/Attendance.scss");
</style>
