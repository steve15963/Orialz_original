import { createSlice } from '@reduxjs/toolkit'

export const userSlice = createSlice({
	name: 'user',
	initialState: {
		email: "",
		nickname: "",
		filter: [
			{
				keyword:'bug',
				filter: false
			},
			{
				keyword:'knife',
				filter: false
			},
			{
				keyword:'monkey',
				filter: false
			},
			{
				keyword:'clown',
				filter: false
			},
			{
				keyword:'mouse',
				filter: false},
			{
				keyword:'chair',
				filter: false
			},
			{
				keyword:'ghost',
				filter: false
			},
			
		]
  	},
  	reducers: {
		uploadUser: (state, user) => {
			state.email = user.payload.email;
			state.nickname = user.payload.nickname;
		},
		discardUser: (state) => {
			state.email = "asd";
			state.nickname = "asd";
		},
		uploadFilter: (state, newFilter) => {
			state.filter = newFilter.payload;
		}
  	},
})

export const { uploadUser, discardUser, uploadFilter } = userSlice.actions

export const selectUser = (state) => state.user;

// The function below is called a thunk and allows us to perform async logic. It
// can be dispatched like a regular action: `dispatch(incrementAsync(10))`. This
// will call the thunk with the `dispatch` function as the first argument. Async
// code can then be executed and other actions can be dispatched
// export const incrementAsync = (amount) => (dispatch) => {
//   setTimeout(() => {
//     dispatch(incrementByAmount(amount))
//   }, 1000)
// }

// The function below is called a selector and allows us to select a value from
// the state. Selectors can also be defined inline where they're used instead of
// in the slice file. For example: `useSelector((state) => state.counter.value)`