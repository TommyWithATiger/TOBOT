<template>
  <div class="page-content">
    <h1>Topics</h1>
    <h2>Add topic</h2>
    <div class="topic-add-fields">
      <label>Title: </label>
      <input @keydown.enter="addTopic" v-model="topic.title" type="text" />
      <br>
      <label>Description: </label>
      <input @keydown.enter="addTopic" v-model="topic.description" type="text" />
      <p>
        <button @click="addTopic">Add</button>
        <span class="error">{{ addFeedback }}</span>
      </p>
    </div>
     <h2>All topics</h2>
      <div v-if="topics.length">
        <div class="topic-info topic-info-header">
          <div class="topic-title">Title</div>
          <div class="topic-description">Description</div>
          <div class="topic-rating-header">My knowledge</div>
        </div>
      </div>
    <TopicRatingList />
  </div>
</template>

<script>
import { api } from 'api'
import TopicRatingList from 'components/template/TopicRatingList'

export default {
  name: 'topicpage',
  data () {
    return {
      topic: {
        title: '',
        description: ''
      },
      addFeedback: '',
      topics: {},
      ratingToNumber: {
        None: 1,
        Poor: 2,
        Ok: 3,
        Good: 4,
        Superb: 5
      },
      numberToRating: {
        '1': 'None',
        '2': 'Poor',
        '3': 'Ok',
        '4': 'Good',
        '5': 'Superb'
      },
      getFeedback: 'Loading ...'
    }
  },
  methods: {
    addTopic () {
      this.addFeedback = ''
      api.addTopic(this, this.topic, (data) => {
        let t = data
        this.topics[t.id] = {
          title: t.title,
          description: t.description,
          id: t.id,
          rating: 0
        }
        this.topics.length++
        this.addFeedback = 'Added to database.'
      }, () => {
        this.addFeedback = 'Failed to add topic.'
      })
    }
  },
  components: {
    TopicRatingList
  }
}
</script>

<style scoped>
.topic-add-fields > label {
  width: 5em;
  display: inline-block;
}

.topic-title, .topic-description {
  padding-right: 20px;
  flex-grow: 1;
  width: 120px;
}

.topic-description {
  flex-grow: 3;
}

.topic-info:nth-child(even) {
  background-color: #ccc;
  background-color: var(--n-color-3);
  border-radius: 4px;
}

.topic-info {
  display: flex;
  flex-flow: row wrap;
  padding: 10px 12px;
}

.topic-info-header {
  font-weight: bold;
  font-size: 1.2em;
}

</style>
