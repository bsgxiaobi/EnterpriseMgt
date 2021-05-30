<template>
	<div id="add-rewards-punishments">
		<el-form :inline="true" v-model="formData"  class="demo-form-inline">
			<el-form-item label="员工编号:">
			  <el-input size="medium" v-model="formData.employeeId" placeholder="员工编号"></el-input>
			</el-form-item>
			<el-form-item label="奖惩类别:">
			  <el-select v-model="formData.type" @change="selectTypeChange()" placeholder="奖惩类别">
			    <el-option label="奖励" value="奖励"></el-option>
			    <el-option label="惩罚" value="惩罚"></el-option>
			  </el-select>
			</el-form-item>
			<el-form-item label="处理类型:">
			  <el-select v-model="formData.category" placeholder="处理类型">
			    <el-option v-show="formData.type == '惩罚'?true:false" label="早退" value="早退"></el-option>
			    <el-option v-show="formData.type == '惩罚'?true:false" label="迟到" value="迟到"></el-option>
				<el-option v-show="formData.type == '惩罚'?true:false" label="工作失误" value="工作失误"></el-option>
				<el-option v-show="formData.type == '奖励'?true:false" label="优秀建议奖" value="优秀建议奖"></el-option>
				<el-option v-show="formData.type == '奖励'?true:false" label="业务突出奖" value="业务突出奖"></el-option>
				<el-option v-show="formData.type == '奖励'?true:false" label="特殊贡献奖" value="特殊贡献奖"></el-option>
			  </el-select>
			</el-form-item>
			<el-form-item label="处理方式:">
			  <el-select v-model="formData.way" placeholder="处理方式">
			    <el-option v-show="formData.type == '奖励'?true:false" label="奖金" value="奖金"></el-option>
				<el-option v-show="formData.type == '奖励'?true:false" label="通报表扬" value="通报表扬"></el-option>
				<el-option v-show="formData.type == '惩罚'?true:false" label="罚款" value="罚款"></el-option>
			    <el-option v-show="formData.type == '惩罚'?true:false" label="通报批评" value="通报批评"></el-option>
			  </el-select>
			</el-form-item>
			<el-form-item v-show="(formData.way == '奖金' || formData.way == '罚款')?true:false" label="金额:">
			  <el-input size="medium" v-model="formData.amount" placeholder="金额"></el-input>
			</el-form-item>
			<div class="el-item-textarea">
				<label id="el-item-textarea-label-frist">详细:</label>
			  <el-input type="textarea" :autosize="{ minRows: 2, maxRows: 6}" size="medium" v-model="formData.cause" placeholder="详细"></el-input>
			</div>
			<div class="el-item-textarea">
				<label for="">备注:</label>
			  <el-input type="textarea" :autosize="{ minRows: 2, maxRows: 6}" size="medium" v-model="formData.note" placeholder="备注"></el-input>
			</div>
		</el-form>
	</div>
</template>

<script>
let formData = {rwrId:null,type:'',category:'',amount:'',way:'',cause:'',note:''};
export default{
	name:'AddRewardsPunishments',
	data(){
		return{
			formData
		}
	},
	methods:{
		resetData(){
			let formDataTemp = {rwrId:null,type:'',category:'',amount:'',way:'',cause:'',note:''};
			this.formData = formDataTemp;
		},
		selectTypeChange(){
			this.formData.category = '';
			this.formData.way = '';
			this.formData.amount = null;
		}
	}
}
</script>

<style lang="scss" scoped>
#add-rewards-punishments{
	width: 100%;
	height: 320px;
	// background-color: #42B983;
	overflow:auto;
}
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
