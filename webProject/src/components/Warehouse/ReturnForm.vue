<template>
	<div id="apply-form-main">
		<el-form :inline="true" v-model="fromData"  class="demo-form-inline">
			
			<el-form-item label="归还备注:">
			  <el-input style="width: 600px;" v-model="fromData.title" placeholder="备注"></el-input>
			</el-form-item>
			<div id="item-input-table">
			<el-table
				:data="tableData"
				stripe
				border
				max-height="300px">
				<el-table-column type="index" :index="indexMethod" width="30"></el-table-column>
				<el-table-column prop="p_id" label="物品编码" width="80"></el-table-column>
				<el-table-column prop="name" label="物品名称" width="100"></el-table-column>
				<el-table-column prop="model" label="规格型号" width="80"></el-table-column>
				<el-table-column prop="pic" label="单位" width="50"></el-table-column>
				<el-table-column prop="num" label="支领数量" width="75"></el-table-column>
				<el-table-column prop="num" label="已归还数量" width="75"></el-table-column>
				<el-table-column label="归还数量" width="75">
					<template #default="scope">
						<el-input class="table-el-input" v-model="scope.row.return_num"></el-input>
					</template>
				</el-table-column>
			</el-table>
			</div>
		
		</el-form>
	</div>
</template>

<script>
let fromData = {
	title:'',
	period:''
};
let tableData = [{
	p_id:'',
	name:'',
	model:'',
	pic:'',
	price:0,
	num:0,
	total:0,
	surplus_num:0,
}
];


export default{
	name:'ReturnForm',
	computed:{
		inputEvent(row){
			console.log(row);
		},
	},
	data(){
		return{
			fromData,tableData,
			agent:'bishugui',
		}
	},
	methods:{
		indexMethod(index){
			return ++index;
		},
		addTableItem(){
			console.log(this.tableData);
			let tebleDataTemp = {
				p_id:'',
				name:'',
				model:'',
				pic:'',
				price:0,
				num:0,
				total:0,
				surplus_num:0,
			};
			this.tableData.push(tebleDataTemp);
		}
	}
}
</script>

<style lang="scss">
/* #item-input-table .el-table td{
	padding: 7px 0px;
}*/
#item-input-table .el-table td .cell{
	padding: 0 0;
	text-align: center;
}
#item-input-table th .cell{
	text-align: center;
	padding: 0px 0px;
}
#item-input-table .el-table__row td{
	padding: 5px 0px;
}
.table-el-input{
	height: 30px;
	line-height: 30px;
	vertical-align: middle;
	font-size: 14px;
}
.table-el-input .el-input__inner{
	padding: 0px 0px;
	border:none;
	height: 30px;
	line-height: 30px;
	text-align: center;
	vertical-align: middle;
	font-size: 14px;
}
.footer-item{
	margin-top: 5px;
}
.footer-item-left{
	float: right;
	width: 200px;
	line-height: 36px;
	height: 36px;
	font-size: 16px;
	text-align: right;
}
</style>
