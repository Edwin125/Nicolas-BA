"use client"
import React, {useEffect, useState} from 'react';
import {format} from 'date-fns';
import {
    Checkbox,
    CircularProgress,
    Paper,
    Switch,
    Table,
    TableBody,
    TableCell,
    TableContainer,
    TableHead,
    TableRow,
    TextField
} from '@mui/material';
import axios from "axios";

const AdminDashboard = () => {
    const [activeIds, setActiveIds] = useState([]);
    const [newId, setNewId] = useState('');
    const [filterActive, setFilterActive] = useState(true);
    const [loading, setLoading] = useState(false);
    const [filterDate, setFilterDate] = useState('');
    const [sortConfig, setSortConfig] = useState({key: 'id', direction: 'ascending'});

    useEffect(() => {
        getConversations();
    }, []);

    const getConversations = () => {
        axios.get("/request/admin/conversation/all")
            .then(res => {
                setActiveIds(res.data)
            })
    }

    const handleAddId = (e) => {
        e.preventDefault()

        if (newId && !activeIds.find(item => item.id === newId)) {
            setLoading(true)
            axios.post("/request/admin/conversation/create/" + newId)
                .then(res => {
                    const conv = res.data

                    const newEntry = {
                        id: conv.id,
                        dateAdded: conv.created,
                        dateDeactivated: null
                    };

                    setActiveIds([...activeIds, newEntry]);
                })
                .finally(() => setLoading(false))

            setNewId('');
        }
    };

    const handleToggleActive = (id) => {

        axios.post("/request/admin/conversation/toggle/" + id)
            .then(res => {
                const conv = res.data

                setActiveIds(activeIds.map(item =>
                    item.id === conv.id ? {...item, dateDeactivated: conv.deactivated ? conv.deactivated : null} : item
                ));
            })

        setActiveIds(activeIds.map(item =>
            item.id === id ? {...item, dateDeactivated: item.dateDeactivated ? null : new Date()} : item
        ));
    };

    const handleFilterDateChange = (e) => {
        setFilterDate(e.target.value);
    };

    const handleSort = (key) => {
        let direction = 'ascending';
        if (sortConfig.key === key && sortConfig.direction === 'ascending') {
            direction = 'descending';
        }
        setSortConfig({key, direction});
    };

    const filteredIds = activeIds.filter(item => {
        const isActive = filterActive ? item.dateDeactivated === null : true;
        const isAfterDate = filterDate ? new Date(item.dateAdded) >= new Date(filterDate) : true;
        return isActive && isAfterDate;
    });

    const sortedIds = filteredIds.sort((a, b) => {
        if (a[sortConfig.key] < b[sortConfig.key]) {
            return sortConfig.direction === 'ascending' ? -1 : 1;
        }
        if (a[sortConfig.key] > b[sortConfig.key]) {
            return sortConfig.direction === 'ascending' ? 1 : -1;
        }
        return 0;
    });

    const TableContent = sortedIds.map(item => (
        <TableRow key={item.id}>
            <TableCell>{item.id}</TableCell>
            <TableCell>{format(new Date(item.dateAdded), 'yyyy-MM-dd HH:mm:ss')}</TableCell>
            <TableCell>{item.dateDeactivated ? format(new Date(item.dateDeactivated), 'yyyy-MM-dd HH:mm:ss') : '-'}</TableCell>
            <TableCell>
                <Switch
                    checked={!item.dateDeactivated}
                    onChange={() => handleToggleActive(item.id)}
                    color="primary"
                />
            </TableCell>
        </TableRow>
    ))

    return (
        <div className="container mx-auto p-4">
            <h1 className="text-2xl font-bold mb-4">Active IDs</h1>
            <div className="mb-4">
                <form
                    onSubmit={handleAddId}
                >
                    <div className="flex">
                        <input
                            type="text"
                            className="border p-2 mr-2"
                            value={newId}
                            onChange={(e) => setNewId(e.target.value)}
                            placeholder="Enter new ID"
                        />
                        {loading ? (
                                <div className="flex self-center">
                                    <CircularProgress size={30}/>
                                </div>) :
                            (
                                <button className="bg-blue-500 text-white p-2 rounded">
                                    Add ID
                                </button>
                            )}
                    </div>
                </form>
            </div>
            <div className="flex justify-end mb-4">
                <div
                    className="flex items-center border px-4 cursor-pointer mr-4"
                    onClick={() => setFilterActive(prev => !prev)}
                >
                    <Checkbox
                        checked={filterActive}
                        disableRipple
                        sx={{
                            paddingLeft: 0,
                        }}
                    />
                    <div>Show active IDs</div>
                </div>
                <div
                    className="border cursor-pointer"
                >
                    <TextField
                        type="date"
                        variant="outlined"
                        value={filterDate}
                        onChange={handleFilterDateChange}
                        sx={{
                            marginLeft: '10px',
                            '& .MuiOutlinedInput-root': {
                                '& fieldset': {
                                    border: 'none',
                                },
                                '&:hover fieldset': {
                                    border: 'none',
                                },
                            },
                            '& .MuiOutlinedInput-input': {
                                '&:hover': {
                                    backgroundColor: 'transparent',
                                },
                            },
                        }}
                    />
                </div>
            </div>
            <TableContainer component={Paper}>
                <Table>
                    <TableHead>
                        <TableRow>
                            <TableCell onClick={() => handleSort('id')} style={{cursor: 'pointer'}}>
                                ID {sortConfig.key === 'id' && (sortConfig.direction === 'ascending' ? '↑' : '↓')}
                            </TableCell>
                            <TableCell onClick={() => handleSort('dateAdded')} style={{cursor: 'pointer'}}>
                                Date
                                Added {sortConfig.key === 'dateAdded' && (sortConfig.direction === 'ascending' ? '↑' : '↓')}
                            </TableCell>
                            <TableCell onClick={() => handleSort('dateDeactivated')} style={{cursor: 'pointer'}}>
                                Date
                                Deactivated {sortConfig.key === 'dateDeactivated' && (sortConfig.direction === 'ascending' ? '↑' : '↓')}
                            </TableCell>
                            <TableCell>Accessible</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {TableContent}
                    </TableBody>
                </Table>
            </TableContainer>
        </div>
    );
};

export default AdminDashboard;
