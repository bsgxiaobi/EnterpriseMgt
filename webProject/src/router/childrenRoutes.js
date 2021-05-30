import Index from '@/layout/Index.vue';
//面板主页
const dashboardRoutes = {
	path: '/dashboard',
	name: 'Dashboard',
	component: Index,
	children: [{
			path: 'workplace',
			name: 'workplace',
			component: () => import('@/views/Dashboard/Workplace.vue')
		}
	]
};

//面板主页
const mySelfRoutes = {
	path: '/center',
	name: 'center',
	component: Index,
	children: [{
			path: 'self',
			name: 'self',
			component: () => import('@/views/Self/Self.vue')
		}
	]
};

//客户关系管理
const customerRoutes = {
	path: '/customer',
	name: 'customer',
	component: Index,
	children: [{
			path: 'company', //客户单位
			name: 'company',
			component: () => import('@/views/Customer/Company.vue'),
		},
		{
			path: 'opportunity', //项目机会
			name: 'opportunity',
			component: () => import('@/views/Customer/projectOpportunity.vue')
		},
		{
			path: 'contract', //项目合同
			name: 'contract',
			component: () => import('@/views/Customer/projectContract.vue')
		},
		{
			path: 'visit', //项目跟踪回访
			name: 'visit',
			component: () => import('@/views/Customer/projectVisit.vue')
		},
	]
};

//人事管理
const personalRoutes = {
	path: '/personal',
	name: 'Personal',
	component: Index,
	children: [{
			path: 'recruit', //招聘管理
			name: 'Recruit',
			component: () => import('@/views/Personal/Recruit.vue')
		},
		{
			path: 'employee', //员工管理
			name: 'employee',
			component: () => import('@/views/Personal/Employee.vue')
		},
		{
			path: 'salary', //薪资管理
			name: 'salary',
			component: () => import('@/views/Personal/Salary.vue')
		},
		{
			path: 'department', //部门管理
			name: 'department',
			component: () => import('@/views/Personal/Department.vue')
		},
		{
			path: 'rewards-punishments', //奖惩管理
			name: 'rewards-punishments',
			component: () => import('@/views/Personal/Rewards-Punishments.vue')
		},
		{
			path: 'attendance', //考勤管理
			name: 'attendance',
			component: () => import('@/views/Personal/Attendance.vue')
		},
	]
};

//采购管理
const purchaseRoutes = {
	path: '/purchase',
	name: 'purchase',
	component: Index,
	children: [{
			path: 'apply', //采购申请单
			name: 'apply',
			component: () => import('@/views/Purchase/Apply.vue')
		},
		{
			path: 'inquiry', //采购询价单
			name: 'inquiry',
			component: () => import('@/views/Purchase/Inquiry.vue')
		}
	]
};

//销售管理
const salesRoutes = {
	path: '/sales',
	name: 'sales',
	component: Index,
	children: [{
			path: 'quotation', //销售报价单
			name: 'quotation',
			component: () => import('@/views/Sales/Quotation.vue')
		},
		{
			path: 'after', //销售售后单
			name: 'after',
			component: () => import('@/views/Sales/After.vue')
		}
	]
};

//仓库管理
const warehouseRoutes = {
	path: '/warehouse',
	name: 'warehouse',
	component: Index,
	children: [{
			path: 'struct', //仓库结构
			name: 'struct',
			component: () => import('@/views/Warehouse/Struct.vue')
		},
		{
			path: 'warehousing', //采购入库单
			name: 'warehousing',
			component: () => import('@/views/Purchase/Warehousing.vue')
		},
		{
			path: 'delivery', //销售出库单
			name: 'delivery',
			component: () => import('@/views/Sales/Delivery.vue')
		},
		{
			path: 'claim', //内部支领单
			name: 'claim',
			component: () => import('@/views/Warehouse/Claim.vue')
		},
		{
			path: 'return', //支领退还单
			name: 'return',
			component: () => import('@/views/Warehouse/Return.vue')
		},
		{
			path: 'detail', //库存明细账
			name: 'detail',
			component: () => import('@/views/Warehouse/Detail.vue')
		}
	]
};

//财务管理
const financeRoutes = {
	path: '/finance',
	name: 'finance',
	component: Index,
	children: [{
			path: 'receivable', //应收应付
			name: 'receivable',
			component: () => import('@/views/Finance/Receivable.vue')
		},{
			path: 'accounting', //账务管理
			name: 'accounting',
			component: () => import('@/views/Finance/Accounting.vue')
		}
	]
};

export {
	dashboardRoutes,
	mySelfRoutes,
	customerRoutes,
	personalRoutes,
	purchaseRoutes,
	salesRoutes,
	warehouseRoutes,
	financeRoutes
};
