<template>

    <v-card outlined>
        <v-card-title>
            MySalesList # {{$route.params.id }}
        </v-card-title>

        <v-card-text>
            <div>
                <String label="UserId" v-model="item.userId" :editMode="editMode" @change="change" />
            </div>
            <div>
                <Number label="PostId" v-model="item.postId" :editMode="editMode" @change="change" />
            </div>
            <div>
                <String label="Status" v-model="item.status" :editMode="editMode" @change="change" />
            </div>
            <div>
                <Date label="CreateDt" v-model="item.createDt" :editMode="editMode" @change="change" />
            </div>
            <div>
                <Date label="UpdateDt" v-model="item.updateDt" :editMode="editMode" @change="change" />
            </div>
            <div>
                <Number label="Price" v-model="item.price" :editMode="editMode" @change="change" />
            </div>
            <StringList offline label="Photos" v-model="item.photos" :editMode="false" @change="change" />
        </v-card-text>
    </v-card>

</template>

<script>
  const axios = require('axios').default;

  export default {
    name: 'MySalesListViewDetail',
    props: {
      value: Object,
    },
    data: () => ({
        item : [],
    }),
    async created() {
      var params = this.$route.params;
      var temp = await axios.get(axios.fixUrl('/mySalesLists/' + params.id))

      this.item = temp.data;

    },
    methods: {
    }
  }
</script>

