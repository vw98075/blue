import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IWord, defaultValue } from 'app/shared/model/word.model';

export const ACTION_TYPES = {
  FETCH_WORD_LIST: 'word/FETCH_WORD_LIST',
  FETCH_WORD: 'word/FETCH_WORD',
  CREATE_WORD: 'word/CREATE_WORD',
  UPDATE_WORD: 'word/UPDATE_WORD',
  DELETE_WORD: 'word/DELETE_WORD',
  RESET: 'word/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IWord>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type WordState = Readonly<typeof initialState>;

// Reducer

export default (state: WordState = initialState, action): WordState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_WORD_LIST):
    case REQUEST(ACTION_TYPES.FETCH_WORD):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_WORD):
    case REQUEST(ACTION_TYPES.UPDATE_WORD):
    case REQUEST(ACTION_TYPES.DELETE_WORD):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_WORD_LIST):
    case FAILURE(ACTION_TYPES.FETCH_WORD):
    case FAILURE(ACTION_TYPES.CREATE_WORD):
    case FAILURE(ACTION_TYPES.UPDATE_WORD):
    case FAILURE(ACTION_TYPES.DELETE_WORD):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_WORD_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10)
      };
    case SUCCESS(ACTION_TYPES.FETCH_WORD):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_WORD):
    case SUCCESS(ACTION_TYPES.UPDATE_WORD):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_WORD):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/words';

// Actions

export const getEntities: ICrudGetAllAction<IWord> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_WORD_LIST,
    payload: axios.get<IWord>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IWord> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_WORD,
    payload: axios.get<IWord>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IWord> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_WORD,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IWord> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_WORD,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IWord> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_WORD,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
