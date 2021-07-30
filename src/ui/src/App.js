import './App.scss';
import TeamPage from './pages/TeamPage';
import MatchPage from './pages/MatchPage';
import HomePage from './pages/HomePage';
import {BrowserRouter as Router,Route,Switch} from 'react-router-dom';

function App() {
  return (
    <div className="App">
      <Router>
          <Switch>
                  <Route path = "/team/:teamName/season/:season">
                     <MatchPage/>
                  </Route>
                  <Route path = "/team/:teamName">
                    <TeamPage/>
                  </Route>
                  <Route path="/">
                    <HomePage />
                  </Route>
          </Switch>
      </Router>
    </div>
  );
}

export default App;
