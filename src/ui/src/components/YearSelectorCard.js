import {React} from 'react';
import {useParams, Link} from 'react-router-dom';

import './YearSelectorCard.scss';

function YearSelectorCard({teamName}){

    var years = [];

    const START_YEAR = 2008;
    const END_YEAR = 2020;

    for(var i=START_YEAR;i<=END_YEAR;i++) {
        years.push(i);
    }

    return (
        <ol className = "YearSelectorCard">
            {years.map( year => (
                <li>
                    <Link  to={`/team/${teamName}/season/${year}`}>{year}</Link>
                </li>
            ))}
        </ol>
    );

}
export default YearSelectorCard;