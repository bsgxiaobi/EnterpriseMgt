<template>
	<div>
		<el-form :inline="true" v-model="formData"  class="demo-form-inline">
			<el-form-item label="加班日期:">
			 <el-date-picker
			   v-model="formData.DateTemp"
			   placeholder="开始日期"
			   :picker-options="pickerOptions">
			 </el-date-picker>
			</el-form-item>
			<el-form-item label="小时数:">
				<el-input v-model="formData.applyHours" placeholder="小时数"></el-input>
		    </el-form-item>
			
			<div class="el-item-textarea">
				<label id="el-item-textarea-label-frist">事由:</label>
			  <el-input type="textarea" :autosize="{ minRows: 2, maxRows: 6}" size="medium" v-model="formData.cause" placeholder="事由"></el-input>
			</div>
		</el-form>
	</div>
</template>

<script>
let formData = {orId: null,cause: '',applyHours:null,DateTemp: ''};
export default{
	name:'OvertimeForm',
	props:{
		isAdd:Boolean
	},
	data(){
		return{
			formData,
			pickerOptions:{
			    //禁用当前日期之前的日期
				disabledDate : time => {
					return time.getTime() > Date.now() // 返回所有时间小于当前时间的值
				}
			}
		}
	},
	methods:{
		resetData(){
			let formDataTemp = {orId: null,cause: '',applyHours:null,DateTemp: ''};
			this.formData = formDataTemp;
		}
	}
}
</script>

<style lang="scss" scoped>

.el-item-textarea{
	width: 100%;
}
.el-item-textarea label{
	display: block;
	padding-top: 20px;
	padding-bottom: 10px;
}
.el-item-textarea #el-item-textarea-label-frist{
	padding-top: 0px;
}
.el-input-site{
	width: 350px;
	padding: 0 0;
}
</style>
