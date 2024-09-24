<template>

    <v-data-table
        :headers="headers"
        :items="myPage"
        :items-per-page="5"
        class="elevation-1"
    ></v-data-table>

</template>

<script>
    const axios = require('axios').default;

    export default {
        name: 'MyPageView',
        props: {
            value: Object,
            editMode: Boolean,
            isNew: Boolean
        },
        data: () => ({
            headers: [
                { text: "id", value: "id" },
                { text: "postId", value: "postId" },
                { text: "userId", value: "userId" },
                { text: "status", value: "status" },
                { text: "createDt", value: "createDt" },
                { text: "updateDt", value: "updateDt" },
                { text: "price", value: "price" },
                { text: "goods", value: "goods" },
            ],
            myPage : [],
        }),
          async created() {
            var temp = await axios.get(axios.fixUrl('/myPages'))

            temp.data._embedded.myPages.map(obj => obj.id=obj._links.self.href.split("/")[obj._links.self.href.split("/").length - 1])

            this.myPage = temp.data._embedded.myPages;
        },
        methods: {
        }
    }
</script>

