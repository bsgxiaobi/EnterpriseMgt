<template>
	<div>
		<el-form :inline="true" v-model="formData"  class="demo-form-inline">

			<el-form-item label="地点:">
				<el-input class="el-input-site" v-model="formData.site" placeholder="地点"></el-input>
			  </el-form-item>
			 <el-form-item label="来回交通:">
			 	<el-select v-model="formData.vehicle" placeholder="交通工具">
					<el-option label="飞机" value="事假"></el-option>
					<el-option label="火车" value="病假"></el-option>
					<el-option label="公共汽车" value="婚假"></el-option>
					<el-option label="公车" value="丧假"></el-option>
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
			<el-form-item label="预支金额:">
				<el-input v-model="formData.advance" placeholder="预支金额"></el-input>
			  </el-form-item>
			<el-form-item label="报销总计:">
				<el-input :disabled="isAdd" v-model="formData.usedAmount" placeholder="报销总计"></el-input>
			</el-form-item>
			<el-form-item label="退补金额:">
				<el-input :disabled="isAdd" v-model="formData.refund" placeholder="退补金额"></el-input>
			  </el-form-item>
			<div class="el-item-textarea">
				<label id="el-item-textarea-label-frist">事由:</label>
			  <el-input type="textarea" :autosize="{ minRows: 2, maxRows: 6}" size="medium" v-model="formData.cause" placeholder="事由"></el-input>
			</div>
			<div class="el-item-textarea">
				<label for="">备注:</label>
			  <el-input type="textarea" :autosize="{ minRows: 2, maxRows: 6}" size="medium" v-model="formData.note" placeholder="备注"></el-input>
			</div>
		</el-form>
	</div>
</template>

<script>
let formData = {btId: null,cause: '',note: '',site:'',
	vehicle:'',advance:'',usedAmount:'',refund:'',DateTemp: ''
};
export default{
	name:'BusinessTripForm',
	props:{
		isAdd:Boolean
	},
	data(){
		return{
			formData
		}
	},
	methods:{
		resetData(){
			let formDataTemp = {btId: null,cause: '',note: '',site:'',
				vehicle:'',advance:'',usedAmount:'',refund:'',DateTemp: ''
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
.el-input-site{
	width: 350px;
	padding: 0 0;
}
</style>
