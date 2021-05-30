<template>
	<div>
		<el-form :inline="true" v-model="formData"  class="demo-form-inline">
			
			<el-form-item label="类型:">
			  <el-select v-model="formData.type" placeholder="类型">
				<el-option label="事假" value="事假"></el-option>
				<el-option label="病假" value="病假"></el-option>
				<el-option label="婚假" value="婚假"></el-option>
				<el-option label="丧假" value="丧假"></el-option>
				<el-option label="其他" value="其他"></el-option>
			  </el-select>
			</el-form-item>
			<el-form-item label="期间:">
				 <el-date-picker
				  v-model="formData.DateTemp"
				  type="datetimerange"
				  range-separator="至"
				  start-placeholder="开始日期"
				  end-placeholder="结束日期">
				</el-date-picker>
			</el-form-item>
			<div class="el-item-textarea">
				<label id="el-item-textarea-label-frist">原因:</label>
			  <el-input type="textarea" :autosize="{ minRows: 2, maxRows: 6}" size="medium" v-model="formData.cause" placeholder="原因"></el-input>
			</div>
			<div class="el-item-textarea">
				<label for="">备注:</label>
			  <el-input type="textarea" :autosize="{ minRows: 2, maxRows: 6}" size="medium" v-model="formData.note" placeholder="备注"></el-input>
			</div>
		</el-form>
	</div>
</template>

<script>
let formData = {alrId: null,cause: '',note: '',
	type: '',DateTemp: ''
};
export default{
	name:'ApplyLeaveForm',
	data(){
		return{
			formData
		}
	},
	methods:{
		resetData(){
			let formDataTemp = {alrId: null,cause: '',note: '',
				type: '',DateTemp: ''
			};
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
</style>
