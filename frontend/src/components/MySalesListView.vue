<template>

    <v-data-table
        :headers="headers"
        :items="mySalesList"
        :items-per-page="5"
        class="elevation-1"
    ></v-data-table>

</template>

<script>
    const axios = require('axios').default;

    export default {
        name: 'MySalesListView',
        props: {
            value: Object,
            editMode: Boolean,
            isNew: Boolean
        },
        data: () => ({
            headers: [
                { text: "id", value: "id" },
                { text: "postId", value: "postId" },
                { text: "status", value: "status" },
                { text: "createDt", value: "createDt" },
                { text: "updateDt", value: "updateDt" },
                { text: "price", value: "price" },
                { text: "goods", value: "goods" },
                { text: "userId", value: "userId" },
            ],
            mySalesList : [],
        }),
          async created() {
            var temp = await axios.get(axios.fixUrl('/mySalesLists'))

            temp.data._embedded.mySalesLists.map(obj => obj.id=obj._links.self.href.split("/")[obj._links.self.href.split("/").length - 1])

            this.mySalesList = temp.data._embedded.mySalesLists;
        },
        methods: {
        }
    }
</script>

