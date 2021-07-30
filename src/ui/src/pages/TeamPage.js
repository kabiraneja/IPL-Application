import {React,useEffect,useState} from 'react';
import {useParams,Link} from 'react-router-dom';
import MatchDetailCard from '../components/MatchDetailCard';
import MatchSmallCard from '../components/MatchSmallCard';
import './TeamPage.scss';

function TeamPage() {
        const[team,setTeam] = useState({latestMatches : []});
        const{teamName} = useParams();
        const END_YEAR = 2020;
    useEffect(
        () => {
              const getMatches = async() => {
                    const response = await fetch(`http://localhost:8080/matchData/team/${teamName}`);
                    const data = await response.json();
                    setTeam(data);
              };
           getMatches();
        },[teamName]
)

   return (
       <div className="TeamPage">

         <div className="team-name-section">
               <h1 className="team-name">{team.teamName}</h1>
           </div>

         <div className="match-detail-section">
         <MatchDetailCard teamName={team.teamName} match={team.latestMatches[0]} />
         </div>

         {team.latestMatches.slice(1).map(match => <MatchSmallCard teamName={team.teamName} match={match} />)}

           <div className="more-link">
               <Link to={`/team/${teamName}/season/${END_YEAR}`}> More </Link>
           </div>

       </div>
     );
}

export default TeamPage;