import { configureStore} from '@reduxjs/toolkit'
import {userSlice} from '../util/slice/userSlice'

export default configureStore({
    reducer: {
        user: userSlice.reducer,
    },
})