import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Word from './word';
import WordDetail from './word-detail';
import WordUpdate from './word-update';
import WordDeleteDialog from './word-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={WordDeleteDialog} />
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={WordUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={WordUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={WordDetail} />
      <ErrorBoundaryRoute path={match.url} component={Word} />
    </Switch>
  </>
);

export default Routes;
