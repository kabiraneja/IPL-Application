import {React,useEffect,useState} from 'react';
import {useParams,Link} from 'react-router-dom';
import MatchDetailCard from '../components/MatchDetailCard';
import YearSelectorCard from '../components/YearSelectorCard';
import './MatchPage.scss';
function MatchPage() {
            const[Matches,setMatch] = useState([]);
            const{teamName,season} = useParams();

         useEffect(
                () => {

                      const getAllMatchesWon = async() => {
                            const response = await fetch(`http://localhost:8080/matchData/season/${season}/winner/${teamName}`);
                            const data = await response.json();
                            setMatch(data);
                      };
                   getAllMatchesWon();
                },[teamName, season]
)
return (
    <div className="MatchPage">

    <div className="year-selector-card">
        <h3> Select Year </h3>
        <YearSelectorCard teamName={teamName}/>
    </div>

    <div>
      <h1 className="page-heading">Matches won by {teamName} in {season}</h1>

      <h1 className="home-page-link">
            <Link to={`/`}>Home</Link>
      </h1>

      {
        Matches.map(match => <MatchDetailCard teamName={teamName} match={match}/>)
      }
     </div>
    </div>
  );
}

export default MatchPage;

