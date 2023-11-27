<template>
    <div class="table-container">
    <h2>기본통계</h2>
    </div>
    <div>
    <b-table sticky-header :items="items" head-variant="light" :align="center">
        <template #cell(순서)="item">
        <div class="table-cell">{{ item.statNum }}</div>
        </template>
        <template #cell(제목)="item">
        <div class="table-cell">{{ item.statTitle }}</div>
        </template>
        <template #cell(조회수)="item">
        <div class="table-cell">{{ item.statView }}</div>
        </template>
        <template #cell(좋아요)="item">
        <div class="table-cell">{{ item.statLikeCnt }}</div>
        </template>
        <template #cell(수익)="item">
        <div class="table-cell">{{ item.statPayAvg }}</div>
        </template>
        <template #cell(평균별점)="item">
        <div class="table-cell">{{ item.statStarAvg }}</div>
        </template>
    </b-table>
    </div>
</template>

<script>
    export default {
    data() {
        return {
        items: [
            {순서: 'statNum', 제목: 'statTitle', 조회수: 'statView', 좋아요: 'statLikeCnt', 수익: 'statPayCnt', 평점: 'statStarAvg'},
            ]
        }
    }
    }
</script>
<style>
.table-container {
margin: 20px;
}

.table-cell {
padding: 10px;
border: 1px solid #ccc;
}

.table-header {
background-color: #f5f5f5;
font-weight: bold;
}

.table-row:nth-child(even) {
background-color: #f9f9f9;
}

.table-row:hover {
background-color: #e5e5e5;
}
</style>