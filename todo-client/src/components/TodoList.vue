<template>
  <div class="todo">
    <img src="../assets/logo.png" width="75px" height="75px">
    <h1 class="mt-3">Vue.js Custom Todo-List</h1>


    <div class="container">
      <section>
        <div class="row justify-content-center mt-4">
          <input v-model="todo.name" v-on:keyup.enter="addTodo" class="mr-1" placeholder="Todo Item" />
          <button @click="addTodo" class="btn btn-primary">Add Todo</button>
        </div>
      </section>

      <section class="container">
        <div class="row">
          <div class="offset-md-3 col-md-6 mt-3">
            <ul class="list-group justify-content-center">
              <li class="row list-group-item border mt-2 col-xs-1" v-for="todo in todoList">
                <div class="row align-items-center">
                  <input type="checkbox" v-on:change="toggle(todo)" v-bind:checked="todo.active" class="col-sm-1 border border-danger">
                  <del v-if="todo.active" class="col-sm-8">
                    <h5>{{ todo.name }}</h5>
                  </del>
                  <span v-else class="col-sm-8">
                           <h5>{{ todo.name }}</h5>
                        </span>
                  <span @click="deleteTodo(todo)" class="offset-sm-1 col-sm-2 delete text-right">X</span>
                </div>
              </li>
            </ul>
          </div>
        </div>
      </section>

    </div>

  </div>
</template>

<script>
export default {
  name: 'TodoList',
  data () {
    return {
      todo : {},
      todoList: []
    }
  },
  methods: {
    fetchTodoList(){
      this.$http.get('http://localhost:8080/main/list')
        .then(result => result.json()
          .then(data => data.forEach(todo => this.todoList.push(todo))));
    },

    addTodo(e){

      let newTodo = {
        name: this.todo.name
      };

      this.$http.post('http://localhost:8080/main', newTodo)
        .then(result => result.json().then(data => {
          this.todoList.push(data);
          this.todo.name = '';
        }));


      e.preventDefault();
    },

    toggle(todo) {
      todo.active = !todo.active;
    },

    deleteTodo(todo){
      const id = todo.id;

      this.$http.delete('http://localhost:8080/main/delete/' + id)
        .then(result =>{
          if(result.ok) {
            this.todoList.splice(this.todoList.indexOf(this.todo), 1)
          }
        });
    }
  },

  created(){
    this.fetchTodoList();
  }
}
</script>

<style scoped>
  h1, h2 {
    font-weight: normal;
  }
  h5 {
    margin-bottom: 0px;
  }
  ul {
    list-style-type: none;
    padding: 0;
  }
  li {
    display: inline-block;
    margin: 0 10px;
  }
  a {
    color: #42b983;
  }
  .delete {
    color: pink;
    cursor: pointer;
  }
  .delete:hover {
    color: red;
  }
</style>
